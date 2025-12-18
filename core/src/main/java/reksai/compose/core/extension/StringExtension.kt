package reksai.compose.core.extension

import android.annotation.SuppressLint
import android.util.Base64
import java.security.MessageDigest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.math.sign

/**
 * 电话 变为 111-1111-1111 的格式
 */
fun String.formatPhoneNumber(): String {
    val formattedValue = this.filter { it.isDigit() }.take(11)
    return when {
        formattedValue.length <= 3 -> formattedValue
        formattedValue.length <= 7 -> "${formattedValue.substring(0, 3)}-${formattedValue.substring(3)}"
        else -> "${formattedValue.substring(0, 3)}-${formattedValue.substring(3, 7)}-${formattedValue.substring(7)}"
    }
}

/**
 * 生成一个 UUID 字符串
 */
fun String.Companion.uuid(): String {
    return UUID.randomUUID().toString()
}

/**
 * 星号加密字符串
 * @param startIndex 1
 * @param endIndex Int
 * @return String
 */
fun String.toEncryption(startIndex: Int, endIndex: Int): String {
    return try {
        this.replaceRange(startIndex until endIndex, "*".repeat(endIndex - startIndex))
    } catch (e: Exception) {
        ""
    }
}

/**
 * 正则得到字符串中的手机号码
 * @return List<String>
 */
fun String.extractPhoneNumbers(): List<String> {
    // 手机号码格式
    val regex = """\b1[3-9]\d{9}\b""".toRegex()
    return regex.findAll(this)
        .map { it.value }
        .toList()
}

/**
 * 字符串价格格式化
 * @receiver String
 * @param roundUp Boolean true:四舍五入, false:向下取整
 * @param showDecimal Boolean true:显示小数, false:不显示小数
 * @return String
 */
@SuppressLint("DefaultLocale")
fun String.toPriceFormat(roundUp: Boolean = true, showDecimal: Boolean = true): String {
    val price = this.toDoubleOrNull() ?: 0.0
    if (price == 0.0) {
        return "0"
    }
    return price.toPriceFormat(roundUp, showDecimal)
}

@SuppressLint("DefaultLocale")
fun String.toPriceKrFormat(roundUp: Boolean = false, showDecimal: Boolean = false): String {
    return this.toPriceFormat(roundUp, showDecimal)
}

/**
 * 商品详情html模版
 */
fun String.toProductHtml(): String {
    return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"  name="viewport"/>
                <title></title>
            </head>
            <body>
                <style>
                    body,html,img,table,div{
                        width: 100% !important;
                        height: auto !important;
                        margin: 0 !important;
                        padding: 0 !important;
                    }
                </style>
                
                $this
                
                <script>
                    //给所有的img 添加 click 事件
                    var imgs = document.getElementsByTagName('img');
                    alert('1');
                    var imgList = [];
                    for (var i = 0; i < imgs.length; i++) {
                        imgList.push(imgs[i].getAttribute('src'));
                        imgs[i].onclick = function () {
                            //得到当前点击的img的src
                            var src = this.getAttribute('src');
                            //JsToAndroidProduct.onImageClick(src);
                            JsToAndroidProduct.onImageListClick(src, JSON.stringify(imgList));
                        }
                    }
                </script>
            </body>
            </html>
        """.trimIndent()
}

fun String.toNoticeHtml(): String {
    return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta content="width=device-width, initial-scale=0.5" name="viewport"/>
                <title></title>
            </head>
            <body>
                <style>
                    body,html{
                        width: 100% !important;
                        height: auto !important;
                        margin: 0 !important;
                        padding: 0 !important;
                    }
                </style>
                $this
                <script>
                    //给所有的img 添加 click 事件
                    var imgs = document.getElementsByTagName('img');
                    alert('1');
                    var imgList = [];
                    for (var i = 0; i < imgs.length; i++) {
                        imgList.push(imgs[i].getAttribute('src'));
                        imgs[i].onclick = function () {
                            //得到当前点击的img的src
                            var src = this.getAttribute('src');
                            //JsToAndroidProduct.onImageClick(src);
                            JsToAndroidProduct.onImageListClick(src, JSON.stringify(imgList));
                        }
                    }
                </script>
            </body>
            </html>
        """.trimIndent()
}

fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
}

/**
 * 支持 1~4 段数字版本号，不足 4 段自动补 0 对齐
 * 返回  1  表示当前版本 > other
 *       0  表示相等
 *      -1  表示当前版本 < other
 */
fun String.compareVersion(other: String): Int {
    fun String.toIntList() = split('.').map { it.toIntOrNull() ?: 0 }
        .let { if (it.size < 4) it + List(4 - it.size) { 0 } else it.take(4) }

    val a = this.toIntList()
    val b = other.toIntList()
    for (i in 0..3) {
        val d = a[i] - b[i]
        if (d != 0) return d.sign
    }
    return 0
}

/**
 * 字符串转 LocalDateTime
 */
fun String.toLocalDateTime(fmt: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime? {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(fmt)

    return try {
        LocalDateTime.parse(this, dateTimeFormatter)
    } catch (e: Exception) {
        null
    }
}

/**
 * 字符串转 LocalDate
 */
fun String.toLocalDate(fmt: String = "yyyy-MM-dd"): LocalDate? {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(fmt)

    return try {
        LocalDate.parse(this, dateTimeFormatter)
    } catch (e: Exception) {
        null
    }
}

/**
 * 字符串转 Int, 转换失败返回 0
 */
fun String.toIntOrZero(): Int {
    return this.toIntOrNull() ?: 0
}

/**
 * 字符串转 Double, 转换失败返回 0.0
 */
fun String.toDoubleOrZero(): Double {
    return this.toDoubleOrNull() ?: 0.0
}

/**
 * 空字符串替换显示
 */
fun String.toEmptyDisplay(replaceList: List<String> = listOf("", "0원(￥0)", "CNY 0"), newValue: String = "-"): String {
    return if (replaceList.contains(this)) {
        newValue
    } else {
        this
    }
}

/**
 * 字符串转 MD5
 * @param isUpperCase Boolean 是否大写
 */
fun String.toMD5(isUpperCase: Boolean = false): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    val hashBytes = messageDigest.digest(this.toByteArray())
    val res = hashBytes.joinToString("") { "%02x".format(it) }

    return if (isUpperCase) res.uppercase() else res
}


