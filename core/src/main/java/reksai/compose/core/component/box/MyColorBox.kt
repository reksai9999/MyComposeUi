package reksai.compose.core.component.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.MyTheme

@Composable
fun MyColorBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    startColor: Color = Color.Red,
    endColor: Color = Color.White,
    isHorizontal: Boolean = true,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val brush = if (isHorizontal) Brush.horizontalGradient(
        colors = listOf(startColor, endColor)
    )
    else Brush.verticalGradient(
        colors = listOf(startColor, endColor)
    )

    Box(
        contentAlignment = contentAlignment,
        modifier = modifier.background(brush = brush)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun MyColorBoxPreview() {
    MyTheme {
        MyColorBox(
            modifier = Modifier.size(200.dp),
            startColor = Color.Blue,
            endColor = Color.Cyan,
            isHorizontal = true
        )
    }
}
