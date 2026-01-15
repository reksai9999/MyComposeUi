package reksai.compose.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//long 转 yyyy-MM-dd HH:mm:ss 字符串
fun Long.toDateTimeString(fmt: String = "yyyy-MM-dd HH:mm:ss"): String {
    return SimpleDateFormat(fmt, Locale.getDefault()).format(Date(this))
}

//long 转 yyyy-MM-dd
fun Long.toDateString(fmt: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(fmt, Locale.getDefault()).format(Date(this))
}

//long 转 yy.MM.dd
fun Long.toDateShortString(fmt: String = "yy.MM.dd"): String {
    return SimpleDateFormat(fmt, Locale.getDefault()).format(Date(this))
}