package reksai.compose.core.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import reksai.compose.core.component.base.MyCard
import reksai.compose.core.component.icon.MyIconClose
import reksai.compose.core.extension.clickableNormalNoEffect

@Composable
fun MyDialog(
    show: Boolean,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
    showCloseIcon: Boolean = false,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    closeIconComposer: @Composable (() -> Unit)? = null,
    content: @Composable (hide: () -> Unit) -> Unit = {}
) {
    if (show) {
        val scope = rememberCoroutineScope()
        Dialog(
            onDismissRequest = {
                onHide()
            },
            properties = DialogProperties(
                dismissOnClickOutside = dismissOnClickOutside,
                dismissOnBackPress = dismissOnBackPress
            )
        ) {
            MyCard(
                modifier = modifier
            ) {
                Box {
                    if (showCloseIcon) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .clickableNormalNoEffect {
                                    scope.launch {
                                        onHide()
                                    }
                                }
                        ) {
                            if (closeIconComposer != null) {
                                closeIconComposer()
                            } else {
                                MyIconClose(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                    content {
                        scope.launch {
                            onHide()
                        }
                    }
                }
            }
        }
    }
}