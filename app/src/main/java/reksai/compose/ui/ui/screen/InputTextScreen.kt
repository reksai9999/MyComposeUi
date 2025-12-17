package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyInputText
import reksai.compose.core.component.icon.MyIconSearch
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputTextScreen(
    modifier: Modifier = Modifier,
) {
    val state = rememberTextFieldState()
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Input Text",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                label = {
                    Text(
                        text = "ABC",
                        style = LocalTypography.current.bodySmall,
                        color = LocalColors.current.black200,
                        modifier = Modifier
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                lineLimits = 10,
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                suffix = {
                    MyIconSearch(modifier = Modifier.size(18.dp))
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .border(1.dp, LocalColors.current.gray300, LocalShapes.current.small)
            )
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