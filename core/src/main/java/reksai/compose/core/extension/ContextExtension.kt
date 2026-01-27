package reksai.compose.core.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * 扩展函数：快速复制文本到剪贴板
 */
fun Context.copyToClipboard(
    text: String,
    label: String = "User Copy",
    onSuccess: (() -> Unit)? = null,
) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    onSuccess?.invoke()
}