package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.ClickablePart
import reksai.compose.core.component.base.MyColorText
import reksai.compose.core.theme.LocalColors
import reksai.compose.ui.utils.tip

@Composable
fun ColorTextScreenVM(
) {
    ColorTextScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ColorTextScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Color Text",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(15.dp)
        ) {

            MyColorText(
                fullText = "This is a sample text with clickable parts.",
                clickableParts = listOf(
                    ClickablePart(
                        text = "sample",
                        color = Color.Red
                    ),
                    ClickablePart(
                        text = "clickable",
                        color = Color.Blue,
                        onClick = {
                            tip("你点击了 clickable")
                        }
                    )
                )
            )

            MyColorText(
                text = "(必填) 名字",
                colorText = "(必填)",
            )

        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun ColorTextScreenPreview() {
    ColorTextScreen(
        modifier = Modifier.fillMaxSize()
    )
}