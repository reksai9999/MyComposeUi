package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
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
import reksai.compose.core.theme.LocalTypography
import reksai.compose.core.transformation.input.ChinaPhoneNumberInputTransformation
import reksai.compose.core.transformation.input.UsaPhoneNumberInputTransformation
import reksai.compose.core.transformation.output.ChinaPhoneNumberOutputTransformation
import reksai.compose.core.transformation.output.UsaPhoneNumberOutputTransformation

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
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                label = { Text("Label") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 1, maxHeightInLines = 10),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                suffix = {
                    MyIconSearch(modifier = Modifier
                        .padding(start = 5.dp)
                        .size(18.dp))
                },
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                prefix = {
                    MyIconSearch(tint = LocalColors.current.red, modifier = Modifier.size(18.dp))
                },
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                modifier = Modifier
                    .fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                supportingText = {
                    Text(
                        text = "supportingText",
                        style = LocalTypography.current.bodySmall,
                        color = LocalColors.current.red,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "MyInputText placeholder",
                isError = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "usa phone",
                inputTransformation = UsaPhoneNumberInputTransformation(),
                outputTransformation = UsaPhoneNumberOutputTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
            )

            MyInputText(
                state = state,
                placeholder = "china phone",
                inputTransformation = ChinaPhoneNumberInputTransformation(),
                outputTransformation = ChinaPhoneNumberOutputTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(100.dp))

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