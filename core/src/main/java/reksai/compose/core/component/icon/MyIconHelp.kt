package reksai.compose.core.component.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import reksai.compose.core.R
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@Composable
fun MyIconHelp(
    modifier: Modifier = Modifier,
    tint: Color = LocalColors.current.white200,
    backgroundColor: Color = LocalColors.current.blackOpacity60,
    shape: Shape = LocalShapes.current.circle,
    size: Dp = 24.dp,
    padding: Dp = 4.dp,
    onClick: () -> Unit = { }
) {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.icon_help),
        contentDescription = null,
        tint = tint,
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(backgroundColor)
            .padding(padding)
            .clickableNormalNoEffect(onClick)
    )
}

@Composable
fun MyIconHelp(
    modifier: Modifier = Modifier,
    tint: Color = LocalColors.current.black200,
) {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.icon_help),
        contentDescription = null,
        tint = tint,
        modifier = modifier
    )
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyIconHelpPreview() {
    Column {
        MyIconHelp(modifier = Modifier.size(32.dp))
        MyIconHelp(size = 32.dp)

    }
}