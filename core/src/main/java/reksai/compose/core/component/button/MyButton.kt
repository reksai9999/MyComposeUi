package reksai.compose.core.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import reksai.compose.core.extension.clickableNormal
import reksai.compose.core.theme.BaseColors
import reksai.compose.core.theme.BaseShapes
import reksai.compose.core.theme.BaseTypography
import reksai.compose.core.theme.LocalShapes

@Composable
fun MyButton(
    text:String,
    modifier: Modifier = Modifier,
    backgroundColor:Color = BaseColors.current.red200,
    borderColor:Color = BaseColors.current.red200,
    textColor: Color = BaseColors.current.white200,
    fontSize:TextUnit = TextUnit.Unspecified,
    textStyle: TextStyle = BaseTypography.current.labelMedium,
    buttonPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
    shape:Shape = BaseShapes.current.circle,
    onClick: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor, shape = shape)
            .border(1.dp, borderColor, shape = shape)
            .clickableNormal { onClick() }
            .padding(buttonPadding)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun MyButton(
    text:String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = BaseTypography.current.labelMedium,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun MyFillButton(
    text:String,
    modifier: Modifier = Modifier,
    shape: Shape = LocalShapes.current.circle,
    textStyle: TextStyle = BaseTypography.current.labelMedium,
    backgroundColor: Color = BaseColors.current.red200,
    textColor: Color = BaseColors.current.white200,
    buttonPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
    onClick: (() -> Unit)? = null
) {
    val buttonModifier = Modifier
        .then(modifier)
        .clip(shape)
        .then(if (onClick != null) Modifier.clickableNormal { onClick() } else Modifier)
        .background(backgroundColor, shape = shape)
        .border(1.dp, backgroundColor, shape = shape)
        .padding(buttonPadding)

    MyButton(
        text = text,
        modifier = buttonModifier,
        textStyle = textStyle.copy(color = textColor)
    )
}

@Composable
fun MyOutlineButton(
    text:String,
    modifier: Modifier = Modifier,
    shape: Shape = LocalShapes.current.circle,
    textStyle: TextStyle = BaseTypography.current.labelMedium,
    backgroundColor: Color = BaseColors.current.transparent,
    color: Color = BaseColors.current.red200,
    buttonPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
    onClick: (() -> Unit)? = null
) {
    val buttonModifier = Modifier
        .then(modifier)
        .clip(shape)
        .then(if (onClick != null) Modifier.clickableNormal { onClick() } else Modifier)
        .background(backgroundColor, shape = shape)
        .border(1.dp, color, shape = shape)
        .padding(buttonPadding)

    MyButton(
        text = text,
        modifier = buttonModifier,
        textStyle = textStyle.copy(color = color)
    )
}

@Preview
@Composable
private fun MyButtonPreview() {
    Column (
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        MyFillButton(
            text = "Fill Button",
            backgroundColor = Color.Blue,
            textColor = Color.White,
        )

        MyOutlineButton(
            text = "Fill Button",
            color = Color.Red,
        )
    }
}