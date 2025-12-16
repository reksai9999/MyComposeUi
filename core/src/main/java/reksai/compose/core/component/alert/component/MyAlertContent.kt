package reksai.compose.core.component.alert.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyAlertContent(
    title: String,
    content: String,

    modifier: Modifier = Modifier,
    titleStyle: TextStyle = LocalTypography.current.bodyMedium,
    contentStyle: TextStyle = LocalTypography.current.bodySmall,
    contentPadding: PaddingValues = PaddingValues(0.dp),

    confirmText: String = "ok",
    cancelText: String = "cancel",
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},

    titleCompose: @Composable (() -> Unit)? = null,
    contentCompose: @Composable (() -> Unit)? = null,
    buttonCompose: @Composable (() -> Unit)? = null,

    ) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.then(modifier)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            if (titleCompose != null) {
                titleCompose()
            } else {
                Text(
                    text = title,
                    style = titleStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
            }

            if (contentCompose != null) {
                contentCompose()
            } else {
                Text(
                    text = content,
                    style = contentStyle,
                    modifier = Modifier
                )
            }
        }

        if (buttonCompose != null) {
            buttonCompose()
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                MyFillButton(
                    text = confirmText,
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f)
                )

                MyOutlineButton(
                    text = cancelText,
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyAlertContentPreview() {
    MyAlertContent(
        title = "标题",
        content = "这是内容文本",
        confirmText = "确认",
        cancelText = "取消",
    )
}