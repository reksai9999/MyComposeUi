package reksai.compose.core.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val ISO_DATE_FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ")
private val ISO_DATE_FMT_SHORT: DateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM.dd")

private val ISO_DATE_TIME_FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
private val ISO_DATE_TIME_FMT_SHORT: DateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm:ss")

/**
 * LocalDate 转 yyyy-MM-dd 字符串
 */
fun LocalDateTime.toDateString(): String = this.format(ISO_DATE_FMT) ?: ""
fun LocalDateTime.toDateShortString(): String = this.format(ISO_DATE_FMT_SHORT) ?: ""
fun LocalDateTime.toDateTimeString(): String = this.format(ISO_DATE_TIME_FMT) ?: ""
fun LocalDateTime.toDateTimeShortString(): String = this.format(ISO_DATE_TIME_FMT_SHORT) ?: ""

