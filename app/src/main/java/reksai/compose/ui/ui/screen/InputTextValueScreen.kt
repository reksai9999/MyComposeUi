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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyInputText
import reksai.compose.core.component.base.MyInputTextType
import reksai.compose.core.component.icon.MyIconSearch
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputTextValueScreen(
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Input Text Value",
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
                value = value,
                onChangeValue = { value = it },
                placeholder = "MyInputText value/onChangeValue",
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                type = MyInputTextType.Password,
                placeholder = "Password value/onChangeValue",
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                placeholder = "MyInputText with label",
                label = { Text("Label") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                placeholder = "MultiLine value/onChangeValue",
                lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 1, maxHeightInLines = 10),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                placeholder = "With suffix",
                suffix = {
                    MyIconSearch(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(18.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                placeholder = "With prefix",
                prefix = {
                    MyIconSearch(tint = LocalColors.current.red, modifier = Modifier.size(18.dp))
                },
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                placeholder = "Error state",
                isError = true,
                modifier = Modifier.fillMaxWidth()
            )

            MyInputText(
                value = value,
                onChangeValue = { value = it },
                placeholder = "Disabled",
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "当前值: $value",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.gray660,
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun InputTextValueScreenPreview() {
    InputTextValueScreen(
        modifier = Modifier.fillMaxSize()
    )
}
