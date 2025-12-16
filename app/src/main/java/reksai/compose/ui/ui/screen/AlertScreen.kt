package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.alert.MyAlert
import reksai.compose.core.component.alert.MyAlertBottom
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import reksai.compose.ui.ui.component.TopBar

@Composable
fun AlertScreen(
    modifier: Modifier = Modifier,
) {
    var showBottomAlert by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    var showAlertCustom by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        TopBar(
            title = "Alert",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
        ) {

            MyFillButton("Bottom Alert") { showBottomAlert = true }
            MyFillButton("Alert") { showAlert = true }
            MyFillButton("Alert 自定义内容") { showAlertCustom = true }

        }
    }

    MyAlertBottom(
        show = showBottomAlert,
        onHide = { showBottomAlert = false },
        title = "Alert Title",
        content = "This is an alert message to inform you about something important. Please read it carefully.",
    )

    MyAlert(
        show = showAlert,
        onHide = { showAlert = false },
        title = "Alert Title",
        content = "This is an alert message to inform you about something important. Please read it carefully.",
    )

    MyAlert(
        show = showAlertCustom,
        onHide = { showAlertCustom = false },
        titleCompose = {
            Text(
                text = "自定义标题：Alert Title",
                style = LocalTypography.current.bodyMedium,
                color = LocalColors.current.red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        },
        contentCompose = {
            Text(
                text = "自定义内容：This is an alert message to inform you about something important. Please read it carefully.",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.gray660,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        },
        buttonCompose = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                MyFillButton(
                    text = "关闭 Alert",
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    showAlertCustom = false
                }
            }
        }
    )

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun AlertScreenPreview() {
    AlertScreen(
        modifier = Modifier.fillMaxSize()
    )
}