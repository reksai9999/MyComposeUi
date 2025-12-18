package reksai.compose.core.extension

/**
 * 角标处理最大 99+
 */
fun Int.toBadgeFormat(): String {
    return when {
        this <= 0 -> ""
        this > 99 -> "99+"
        else -> this.toString()
    }
}
