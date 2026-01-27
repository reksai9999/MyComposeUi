package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyInputText
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.extension.copyToClipboard
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import reksai.compose.ui.utils.tip

@Composable
fun ExtensionScreenVM() {
    ExtensionScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ExtensionScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "扩展方法",
        )
        Column(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(horizontal = 20.dp)
        ) {
            val clipText = "这是我要复制的文字..." + System.currentTimeMillis()
            Text(
                text = clipText,
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier.padding(top = 20.dp)
            )
            MyFillButton(
                text = "复制",
                modifier = Modifier.width(80.dp)
            ) {
                context.copyToClipboard(clipText) {
                    tip("已复制...")
                }
            }
            val textState1 = rememberTextFieldState()
            MyInputText(
                state = textState1,
                placeholder = "粘贴看看",
                lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = 10),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun ExtensionScreenPreview() {
    ExtensionScreen(
        modifier = Modifier.fillMaxSize()
    )
}