package reksai.compose.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//long 转 yyyy-MM-dd HH:mm:ss 字符串
fun Long.toDateTimeString(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(this))
}

//long 转 yyyy-MM-dd
fun Long.toDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(this))
}

//long 转 yy.MM.dd
fun Long.toDateShortString(): String {
    return SimpleDateFormat("yy.MM.dd", Locale.getDefault()).format(Date(this))
}