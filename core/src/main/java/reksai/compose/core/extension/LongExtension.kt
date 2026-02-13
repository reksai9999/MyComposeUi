package reksai.compose.core.extension

import android.icu.text.RelativeDateTimeFormatter
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

/**
 * 使用 Android 原生 ICU 库将时间戳转为韩文相对时间
 * 适配 API 24+
 */
fun Long.toKoreanRelativeTime(): String {
    // 1. 确保是毫秒级
    val timeInMillis = if (this < 10000000000L) this * 1000 else this
    val now = System.currentTimeMillis()
    val diff = now - timeInMillis

    // 2. 获取 ICU 格式化器实例
    val fmt = RelativeDateTimeFormatter.getInstance(Locale.KOREAN)

    // 3. 根据时间差计算
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "방금" // ICU 默认没有“刚刚”，手动写一个更自然
        minutes < 60 -> fmt.format(minutes.toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.MINUTES)
        hours < 24 -> fmt.format(hours.toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.HOURS)
        days < 30 -> fmt.format(days.toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.DAYS)
        else -> fmt.format((days / 30).toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.MONTHS)
    }
}

/**
 * 使用 Android 原生 ICU 库将时间戳转为中文相对时间
 * 适配 API 24+
 */
fun Long.toChineseRelativeTime(): String {
    // 1. 确保是毫秒级
    val timeInMillis = if (this < 10000000000L) this * 1000 else this
    val now = System.currentTimeMillis()
    val diff = now - timeInMillis

    // 2. 获取 ICU 格式化器实例
    val fmt = RelativeDateTimeFormatter.getInstance(Locale.KOREAN)

    // 3. 根据时间差计算
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "刚刚" // ICU 默认没有“刚刚”，手动写一个更自然
        minutes < 60 -> fmt.format(minutes.toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.MINUTES)
        hours < 24 -> fmt.format(hours.toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.HOURS)
        days < 30 -> fmt.format(days.toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.DAYS)
        else -> fmt.format((days / 30).toDouble(), RelativeDateTimeFormatter.Direction.LAST, RelativeDateTimeFormatter.RelativeUnit.MONTHS)
    }
}