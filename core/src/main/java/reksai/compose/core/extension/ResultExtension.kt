package reksai.compose.core.extension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// 自定义异常，表示内部出错了
class InnerException(message: String = "inner value error") : Exception(message)

/**
 * 一对一依赖
 * uploadFile()
 *     .andThen { updateProfile(it) }
 *     .onSuccess { updateProfileUI() }
 *     .onFailure { showErrorPage() }
 */
@OptIn(ExperimentalContracts::class)
inline fun <V, E> Result<V>.andThen(transform: (V) -> Result<E>): Result<E> {
    // kotlin 约定，告诉编译器 transform 最多执行一次
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    if (isSuccess) {
        val value = getOrNull() ?: return Result.failure(InnerException())
        return transform(value)
    } else {
        val exception = exceptionOrNull() ?: return Result.failure(InnerException())
        return Result.failure(exception)
    }
}

/**
 * 一对多依赖
 * getUserPhoneNumber().dispatch { phoneNumber ->
 *     val aaaVideoPlayNum = getVideoPlayNum(phoneNumber).getOrThrow()
 *     val tiktokVideoPlayNum = getTiktokVideoPlayNum(phoneNumber).getOrThrow()
 *      aaaVideoPlayNum + tiktokVideoPlayNum
 * }.onFailure { println("onFailure $it") }
 *  .onSuccess { println("onSuccess $it") }
 */
inline fun <V, E> Result<V>.dispatch(transform: (V) -> E): Result<E> {
    if (isSuccess) {
        val value = getOrNull() ?: return Result.failure(InnerException())
        return runCatching {
            transform(value)
        }
    } else {
        val exception = exceptionOrNull() ?: return Result.failure(InnerException())
        return Result.failure(exception)
    }
}

/**
 * 多对一依赖
 * zip {
 *     val userInfo = getUserInfo().getOrThrow()
 *     val invoiceNumber = getInvoiceNumber().getOrThrow()
 *     userInfo to invoiceNumber
 * }.andThen {
 *     reimbursement(it.first, it.second)
 * }.onFailure { println("onFailure $it") }
 *     .onSuccess { println("onSuccess $it") }
 */
inline fun <V> resultZip(block: () -> V): Result<V> {
    return runCatching {
        block()
    }
}

/**
 * 选择关系 比如说手机付款，先用零钱余额支付，如果支付失败（比如余额不足）则使用花呗支付
 * payByA().or(payByB())
 *     .onFailure { println("onFailure $it") }
 *     .onSuccess { println("onSuccess $it") }
 */
fun <V> Result<V>.or(result: Result<V>): Result<V> {
    return when {
        isSuccess -> this
        else -> result
    }
}