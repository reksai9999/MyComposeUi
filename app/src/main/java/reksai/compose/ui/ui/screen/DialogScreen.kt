package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.component.dialog.MyBottomDialog
import reksai.compose.core.component.dialog.MyDialog
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import reksai.compose.ui.ui.component.TopBar

@Composable
fun DialogScreen(
    modifier: Modifier = Modifier,
) {
    var showBottomDialog by remember { mutableStateOf(false) }
    var isDrag by remember { mutableStateOf(false) }
    var isFull by remember { mutableStateOf(true) }
    var height by remember { mutableStateOf(0.8f) }


    var showDialog by remember { mutableStateOf(false) }
    var dismissOnClickOutside by remember { mutableStateOf(true) }


    Column (
        modifier = modifier.background(LocalColors.current.background)
    ) {
        TopBar(
            title = "Dialog",
        )
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
        ) {
            MyFillButton("Bottom Dialog") {
                showBottomDialog = true
                isDrag = false
                isFull = true
                height = 0.8f
            }
            MyFillButton("Bottom Dialog 可以拖拽") {
                showBottomDialog = true
                isDrag = true
                isFull = true
                height = 0.6f
            }
            MyFillButton("Bottom Dialog 可以拖拽, 半屏->全屏") {
                showBottomDialog = true
                isDrag = true
                isFull = false
                height = 1f
            }

            MyFillButton("Dialog") {
                showDialog = true
                dismissOnClickOutside = true
            }

            MyFillButton("Dialog 不可点击外部关闭") {
                showDialog = true
                dismissOnClickOutside = false
            }
        }
    }

    MyBottomDialog(
        show = showBottomDialog,
        isDrag = isDrag,
        showDragHandle = true,
        isExpanded = isFull,
        showCloseIcon = true,
        onHide = { showBottomDialog = false },
    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(height)
        ) {
            content()
        }
    }

    MyDialog(
        show = showDialog,
        onHide = { showDialog = false },
        showCloseIcon = true,
        dismissOnClickOutside = dismissOnClickOutside
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            content()
        }
    }

}

@Composable
private fun content(
    modifier: Modifier = Modifier
) {
    Text(
        text = "123",
        style = LocalTypography.current.bodySmall,
        color = LocalColors.current.black200,
        modifier = modifier
    )
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun DialogScreenPreview() {
    DialogScreen(
        modifier = Modifier.fillMaxSize()
    )
}