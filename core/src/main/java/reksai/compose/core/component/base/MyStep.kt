package reksai.compose.core.component.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@Composable
fun MyStep(
    modifier: Modifier = Modifier,
    step: Int,
    total: Int = 4,
    color: Color = LocalColors.current.red200,
    unColor: Color = LocalColors.current.gray500,
    onStepChange: (Int) -> Unit = { _ -> },
) {

    Row (
        modifier = modifier.offset(x = 3.dp)
    ) {
        repeat(total) { index ->
            MyStepRow(
                active = step >= index + 1,
                color = color,
                unColor = unColor,
                onClick = { onStepChange(index + 1) },
                modifier = Modifier.weight(1f)
                    .zIndex((total - index).toFloat())
            )
        }

    }
}

@Composable
private fun MyStepRow(
    modifier: Modifier = Modifier,
    active: Boolean = false,
    color: Color = LocalColors.current.red200,
    unColor: Color = LocalColors.current.gray500,
    onClick: () -> Unit = { },
) {
    val currentColor = if (active) color else unColor
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box (
            modifier = Modifier
                .offset(x = (-3).dp)
                .fillMaxWidth()
                .height(6.dp)
                .clip(LocalShapes.current.circle)
                .background(currentColor)
        )

        Box (
            modifier = Modifier
                .size(16.dp)
                .clip(LocalShapes.current.circle)
                .scale(1f)
                .align(Alignment.CenterEnd)
                .background(LocalColors.current.white200)
                .border(3.dp, color = currentColor, shape = LocalShapes.current.circle)
                .clickableNormalNoEffect {
                    if (active) onClick()
                }
        )

    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyStepPreview() {
    Column (
        modifier = Modifier.fillMaxWidth().padding(15.dp)
    ) {
        MyStep(
            step = 1,
            total = 3,
            modifier = Modifier
        )
    }
}