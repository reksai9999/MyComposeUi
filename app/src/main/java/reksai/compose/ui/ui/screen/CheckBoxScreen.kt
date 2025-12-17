package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyCheckBox
import reksai.compose.core.component.base.MyCheckBoxText
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@Composable
fun CheckBoxScreen(
    modifier: Modifier = Modifier,
) {
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "CheckBox",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
        ) {
            MyCheckBox(
                checked = isChecked,

                ) {
                isChecked = it
            }

            MyCheckBoxText(
                text = "一个选项",
                checked = isChecked,
                shape = LocalShapes.current.circle,
            ) {
                isChecked = it
            }

            MyCheckBox(
                checked = isChecked,
                shape = LocalShapes.current.circle,
                modifier = Modifier.size(24.dp)
            ) {
                isChecked = it
            }

            MyCheckBox(
                checked = isChecked,
                shape = LocalShapes.current.small,
                modifier = Modifier.size(24.dp)
            ) {
                isChecked = it
            }


            MyCheckBox(
                checked = isChecked,
                shape = LocalShapes.current.none,
                selectBackgroundColor = LocalColors.current.black200,
                selectBorderColor = LocalColors.current.black200,
                unSelectBorderColor = LocalColors.current.black200,
                modifier = Modifier.size(24.dp)
            ) {
                isChecked = it
            }
        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun CheckBoxScreenPreview() {
    CheckBoxScreen(
        modifier = Modifier.fillMaxSize()
    )
}