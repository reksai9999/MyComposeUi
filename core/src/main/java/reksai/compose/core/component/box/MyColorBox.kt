package reksai.compose.core.component.box

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography

enum class MyColorBoxType {
    /**
     * 水平渐变
     */
    Horizontal,

    /**
     * 垂直渐变
     */
    Vertical,

    /**
     * 环形渐变
     */
    Radial,
}

@Composable
fun MyColorBox(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        LocalColors.current.transparent,
        LocalColors.current.black200
    ),
    type: MyColorBoxType = MyColorBoxType.Horizontal,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable (BoxScope.() -> Unit)? = null
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = Modifier
            .then(modifier)
            .then(
                if (colors.isEmpty()) {
                    Modifier
                } else {
                    when (type) {
                        MyColorBoxType.Horizontal -> Modifier.background(
                            brush = Brush.horizontalGradient(colors = colors)
                        )

                        MyColorBoxType.Vertical -> Modifier.background(
                            brush = Brush.verticalGradient(colors = colors)
                        )

                        MyColorBoxType.Radial -> Modifier.background(
                            brush = Brush.radialGradient(colors = colors)
                        )
                    }
                }

            )

    ) {
        content?.let {
            it()
        }
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "spec:width=1280px,height=4456px,dpi=480", showBackground = true)
@Composable
private fun MyColorBoxPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        MyColorBox(
            type = MyColorBoxType.Horizontal,
            modifier = Modifier.size(200.dp)
        )

        MyColorBox(
            type = MyColorBoxType.Vertical,
            modifier = Modifier.size(200.dp)
        )

        MyColorBox(
            type = MyColorBoxType.Radial,
            modifier = Modifier.size(200.dp)
        )

        MyColorBox(
            colors = listOf(),
            type = MyColorBoxType.Horizontal,
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, LocalColors.current.black200)
        ) {
            Text(
                text = "123",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        MyColorBox(
            colors = listOf(LocalColors.current.red, LocalColors.current.yellow, LocalColors.current.green, LocalColors.current.blue),
            type = MyColorBoxType.Horizontal,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        MyColorBox(
            colors = listOf(LocalColors.current.red, LocalColors.current.yellow, LocalColors.current.green, LocalColors.current.blue),
            type = MyColorBoxType.Radial,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )


    }
}

