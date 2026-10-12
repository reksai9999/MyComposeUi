package reksai.compose.core.component.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import reksai.compose.core.config.MyGlobalConfig
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

/**
 * 全局 Snackbar 提示组件
 * 使用方式: MyGlobalConfig.sendSnackBarMessage("点击了关闭按钮")
 */
@Composable
fun MySnackBar(
    modifier: Modifier = Modifier,
    closeTime: Long = 2000,
    hostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (SnackbarData) -> Unit = { data ->
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(LocalShapes.current.medium)
                .background(LocalColors.current.blackOpacity80)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = data.visuals.message,
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.white200,
                modifier = Modifier
            )
        }
    }
) {
    LaunchedEffect(Unit) {
        MyGlobalConfig.snackBarMessageFlow.collect { message ->
            if (message.isNotEmpty()) {
                hostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }

    // 1秒后自动关闭
    LaunchedEffect(hostState.currentSnackbarData) {
        if (hostState.currentSnackbarData != null) {
            delay(closeTime)
            hostState.currentSnackbarData?.dismiss()
        }
    }
    SnackbarHost(
        hostState = hostState,
        modifier  = modifier
    ) { data: SnackbarData ->
        content(data)
    }
}