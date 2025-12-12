package reksai.compose.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

/**
 * 节流简化版本，用于无参数的点击事件
 */
@Composable
fun rememberThrottledClick(
    intervalMs: Long = 300L,
    onClick: () -> Unit
): () -> Unit {
    val scope = rememberCoroutineScope()
    return remember(intervalMs) {
        var lastExecuteTime = 0L
        {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastExecuteTime >= intervalMs) {
                lastExecuteTime = currentTime
                scope.launch { onClick() }
            }
        }
    }
}








