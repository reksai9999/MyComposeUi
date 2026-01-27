package reksai.compose.core.component.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import reksai.compose.core.R
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.BaseColors
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    shape: Shape = LocalShapes.current.circle,

    selectBackgroundColor: Color = BaseColors.current.red200,
    unSelectBackgroundColor: Color = BaseColors.current.white200,

    selectBorderColor: Color = BaseColors.current.red200,
    unSelectBorderColor: Color = BaseColors.current.gray300,

    selectIconColor: Color = BaseColors.current.white200,
    unSelectIconColor: Color = BaseColors.current.transparent,
    imageVector: ImageVector = ImageVector.vectorResource(R.drawable.icon_check),
    borderWidth: Dp = 1.3.dp,
    onChange: ((Boolean) -> Unit)? = null,
) {

    val backgroundColor = if (checked) selectBackgroundColor else unSelectBackgroundColor
    val borderColor = if (checked) selectBorderColor else unSelectBorderColor
    val iconColor = if (checked) selectIconColor else unSelectIconColor

    Box (
        modifier = Modifier
            .then(modifier)
            .then(
                if (onChange != null) Modifier.clickableNormalNoEffect { onChange(!checked) }
                else Modifier
            )
            .size(20.dp)
            .background(color = backgroundColor, shape = shape)
            .border(width = borderWidth, color = borderColor, shape = shape)
            .padding(2.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Composable
fun MyCheckBoxText(
    text: String,
    modifier: Modifier = Modifier,

    textStyle: TextStyle = LocalTypography.current.bodySmall,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(2.5.dp),

    checked: Boolean = false,
    shape: Shape = LocalShapes.current.circle,

    selectBackgroundColor: Color = BaseColors.current.red200,
    unSelectBackgroundColor: Color = BaseColors.current.white200,

    selectBorderColor: Color = BaseColors.current.red200,
    unSelectBorderColor: Color = BaseColors.current.gray300,

    selectIconColor: Color = BaseColors.current.white200,
    unSelectIconColor: Color = BaseColors.current.transparent,
    imageVector: ImageVector = ImageVector.vectorResource(R.drawable.icon_check),
    borderWidth: Dp = 1.3.dp,
    onChange: ((Boolean) -> Unit)? = null,
) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier.then(
            if (onChange != null) Modifier.clickableNormalNoEffect { onChange(!checked) }
            else Modifier
        )
    ) {
        MyCheckBox(
            modifier = Modifier,
            checked = checked,
            shape = shape,
            selectBackgroundColor = selectBackgroundColor,
            unSelectBackgroundColor = unSelectBackgroundColor,
            selectBorderColor = selectBorderColor,
            unSelectBorderColor = unSelectBorderColor,
            selectIconColor = selectIconColor,
            unSelectIconColor = unSelectIconColor,
            imageVector = imageVector,
            borderWidth = borderWidth,
        )
        Text(
            text = text,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyCheckBoxPreview() {
    Column (
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        MyCheckBox(
            checked = true,
            shape = LocalShapes.current.circle,
        )

        MyCheckBoxText(
            text = "CheckBox Text",
            checked = false,
            shape = LocalShapes.current.circle,
            modifier = Modifier.size(20.dp)
        )

        MyCheckBox(
            checked = true,
            shape = LocalShapes.current.circle,
            modifier = Modifier.size(20.dp)
        )
        MyCheckBox(
            checked = false,
            shape = LocalShapes.current.circle,
            modifier = Modifier.size(50.dp)
        )
        MyCheckBox(
            checked = true,
            shape = LocalShapes.current.circle,
            modifier = Modifier.size(50.dp)
        )

        MyCheckBox(
            checked = false,
            shape = LocalShapes.current.small,
            modifier = Modifier.size(50.dp)
        )
        MyCheckBox(
            checked = true,
            shape = LocalShapes.current.small,
            modifier = Modifier.size(50.dp)
        )

        MyCheckBox(
            checked = false,
            shape = LocalShapes.current.none,
            unSelectBorderColor = LocalColors.current.black200,
            modifier = Modifier.size(50.dp)
        )
        MyCheckBox(
            checked = true,
            shape = LocalShapes.current.none,
            selectBackgroundColor = LocalColors.current.black200,
            selectBorderColor = LocalColors.current.black200,
            modifier = Modifier.size(50.dp)
        )
    }
}