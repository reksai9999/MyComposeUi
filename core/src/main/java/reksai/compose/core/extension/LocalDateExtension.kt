package reksai.compose.core.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val ISO_DATE_FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
private val ISO_DATE_FMT_SHORT: DateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM.dd")

/**
 * LocalDate 转 yyyy-MM-dd 字符串
 */
fun LocalDate.toDateString(): String = this.format(ISO_DATE_FMT) ?: ""
fun LocalDate.toDateShortString(): String = this.format(ISO_DATE_FMT_SHORT) ?: ""

