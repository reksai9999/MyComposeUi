package reksai.compose.core.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import reksai.compose.core.component.icon.MyIconClose
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomDialog(
    show: Boolean,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    showDragHandle: Boolean = false,
    isDrag: Boolean = true,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (hide:()-> Unit) -> Unit = {}
) {
    if (show) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = !isExpanded)
        var sheetGesturesEnabled by remember { mutableStateOf(isDrag) }
        val scope = rememberCoroutineScope()

        ModalBottomSheet(
            onDismissRequest = onHide,
            sheetState = sheetState,
            sheetGesturesEnabled = sheetGesturesEnabled,
            shape = shape,
            dragHandle = null,
            containerColor = containerColor,
            contentColor = contentColor,
            modifier = modifier,
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                if (showDragHandle) {
                    Box (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box (
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .width(50.dp)
                                .height(4.dp)
                                .clip(LocalShapes.current.circle)
                                .background(LocalColors.current.gray500)
                                .align(Alignment.Center)
                        )
                        MyIconClose(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .align(Alignment.CenterEnd)
                                .clickableNormalNoEffect {
                                    scope.launch {
                                        sheetState.hide()
                                    }
                                }
                        )
                    }
                }

                content {
                    scope.launch {
                        sheetState.hide()
                        onHide()
                    }
                }
            }

        }
    }
}