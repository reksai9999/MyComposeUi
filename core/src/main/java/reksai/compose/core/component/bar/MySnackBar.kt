package reksai.compose.core.component.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MySnackBar(
    hostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier  = modifier
    ) { data: SnackbarData ->
        Box (
            modifier = Modifier
                .padding(16.dp)
                .clip(LocalShapes.current.tiny)
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
}