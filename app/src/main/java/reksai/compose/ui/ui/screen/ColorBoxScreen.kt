package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.box.MyColorBox
import reksai.compose.core.component.box.MyColorBoxType
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography

@Composable
fun ColorBoxScreenVM(
) {
    ColorBoxScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ColorBoxScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Color Box",
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .verticalScroll(rememberScrollState())
        ) {

            MyColorBox(
                type = MyColorBoxType.Horizontal,
                modifier = Modifier.size(200.dp)
            )

            MyColorBox(
                type = MyColorBoxType.Vertical,
                modifier = Modifier.size(200.dp)
            )

            MyColorBox(
                type = MyColorBoxType.Radial,
                modifier = Modifier.size(200.dp)
            )

            MyColorBox(
                colors = listOf(),
                type = MyColorBoxType.Horizontal,
                modifier = Modifier
                    .size(100.dp)
                    .border(1.dp, LocalColors.current.black200)
            ) {
                Text(
                    text = "123",
                    style = LocalTypography.current.bodySmall,
                    color = LocalColors.current.black200,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            MyColorBox(
                colors = listOf(LocalColors.current.red, LocalColors.current.yellow, LocalColors.current.green, LocalColors.current.blue),
                type = MyColorBoxType.Horizontal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            MyColorBox(
                colors = listOf(LocalColors.current.red, LocalColors.current.yellow, LocalColors.current.green, LocalColors.current.blue),
                type = MyColorBoxType.Radial,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun ColorBoxScreenPreview() {
    ColorBoxScreen(
        modifier = Modifier.fillMaxSize()
    )
}