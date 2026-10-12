package reksai.compose.core.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.R
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

/**
 * [onClick] 响应标签点击；仅在提供 [onClose] 时显示关闭图标。
 * [closeIconColor] 默认为文字颜色，关闭后的状态由调用者管理。
 */
@Composable
fun MyTag(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = LocalTypography.current.bodySmall,
    textColor: Color = LocalColors.current.blue500,
    background: Color = LocalColors.current.blue100.copy(alpha = 0.2f),
    border: Color = LocalColors.current.blue100,
    shape: Shape = LocalShapes.current.circle,
    onClick: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    closeIconColor: Color = textColor,
    closeModifier: Modifier = Modifier,
    content: @Composable (() -> Unit)? = null
) {

    Row(
        modifier = modifier
            .background(color = background, shape = shape)
            .border(width = 1.dp, color = border, shape = shape)
            .clip(shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box {
            if (content != null) {
                content()
            } else {
                Text(
                    text = text,
                    style = textStyle,
                    color = textColor,
                    modifier = textModifier
                )
            }
        }
        if (onClose != null) {
            Icon(
                painter = painterResource(R.drawable.icon_close),
                contentDescription = "关闭",
                tint = closeIconColor,
                modifier = Modifier
                    .padding(2.dp)
                    .size(14.dp)
                    .then(closeModifier)
                    .clickable(onClick = onClose)
            )
        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        MyTag(
            text = "Ocean Blue",
            textColor = Color(0xFF1565C0),
            background = Color(0xFFE3F2FD),
            border = Color(0xFF90CAF9),
            onClick = {},
            onClose = {},
            closeIconColor = Color(0xFF1565C0)
        )
        MyTag(
            text = "Mint Green",
            textColor = Color(0xFF1B5E20),
            background = Color(0xFFE8F5E9),
            border = Color(0xFFA5D6A7)
        )
        MyTag(
            text = "Sunset Orange",
            textColor = Color(0xFFE65100),
            background = Color(0xFFFFF3E0),
            border = Color(0xFFFFCC80)
        )
        MyTag(
            text = "Cherry Red",
            textColor = Color(0xFFB71C1C),
            background = Color(0xFFFFEBEE),
            border = Color(0xFFEF9A9A)
        )
        MyTag(
            text = "Lavender Purple",
            textColor = Color(0xFF6A1B9A),
            background = Color(0xFFF3E5F5),
            border = Color(0xFFCE93D8)
        )
        MyTag(
            text = "Rose Pink",
            textColor = Color(0xFFAD1457),
            background = Color(0xFFFCE4EC),
            border = Color(0xFFF48FB1)
        )
        MyTag(
            text = "Golden Yellow",
            textColor = Color(0xFFF57F17),
            background = Color(0xFFFFFDE7),
            border = Color(0xFFFFE082)
        )
        MyTag(
            text = "Teal Fresh",
            textColor = Color(0xFF00695C),
            background = Color(0xFFE0F2F1),
            border = Color(0xFF80CBC4)
        )
        MyTag(
            text = "Slate Gray",
            textColor = Color(0xFF37474F),
            background = Color(0xFFECEFF1),
            border = Color(0xFFB0BEC5)
        )
        MyTag(
            text = "Coffee Brown",
            textColor = Color(0xFF4E342E),
            background = Color(0xFFEFEBE9),
            border = Color(0xFFBCAAA4)
        )
    }

}
