package reksai.compose.core.extension

import android.annotation.SuppressLint
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.round

/**
 * 字符串价格格式化
 * @receiver String
 * @param roundUp Boolean true:四舍五入, false:向上取整
 * @param showDecimal Boolean true:显示小数, false:不显示小数
 * @return String
 */
@SuppressLint("DefaultLocale")
fun Double.toPriceFormat(roundUp: Boolean = true, showDecimal: Boolean = true): String {
    return try {
        val price = this
        val processedPrice = if (roundUp) {
            if (showDecimal) price else round(price)
        } else {
            // 向上取整
            ceil(price)
        }

        val formatter = DecimalFormat()
        if (showDecimal) {
            formatter.applyPattern("#,##0.00")
            formatter.format(processedPrice)
        } else {
            formatter.applyPattern("#,##0")
            formatter.format(processedPrice.toInt())
        }
    } catch (_: Exception) {
        "0"
    }
}

/**
 * 整数价格格式化, 不显示小数
 */
@SuppressLint("DefaultLocale")
fun Int.toPriceFormat(): String {
    return try {
        val formatter = DecimalFormat("#,##0")
        formatter.format(this)
    } catch (_: Exception) {
        "0"
    }
}
