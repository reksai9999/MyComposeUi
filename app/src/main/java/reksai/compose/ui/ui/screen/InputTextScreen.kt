package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import reksai.compose.ui.ui.component.TopBar

@Composable
fun InputTextScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(
            title = "Input Text",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
        ) {

        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun InputTextScreenPreview() {
    InputTextScreen(
        modifier = Modifier.fillMaxSize()
    )
}