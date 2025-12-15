package reksai.compose.core.component.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import kotlin.math.roundToInt

/**
 * 自定义 Switch 组件 用 scale 控制大小
 */
@Composable
fun MySwitch(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = { },
    thumbColor: Color = LocalColors.current.white200,
    trackColor: Color = LocalColors.current.red200,
    scale: Float = 0.8f
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = thumbColor,
            checkedTrackColor = trackColor,
        ),
        modifier = modifier.scaledLayout(scale)
    )
}

// Custom layout modifier that scales both drawing and measured size
private fun Modifier.scaledLayout(scale: Float) = this.then(
    Modifier.layout { measurable, constraints ->
        val s = if (scale <= 0f) 0.0001f else scale
        val placeable = measurable.measure(constraints)
        val width = (placeable.width * s).roundToInt()
        val height = (placeable.height * s).roundToInt()
        layout(width, height) {
            placeable.placeRelativeWithLayer(0, 0) {
                this.scaleX = s
                this.scaleY = s
                this.transformOrigin = TransformOrigin(0f, 0f)
            }
        }
    }
)

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MySwitchPreview() {
    Column (
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
    ) {
        Box (
            modifier = Modifier.background(LocalColors.current.gray300)
        ) {
            MySwitch(
                checked = true,
                scale = 0.4f
            )
        }

        Box (
            modifier = Modifier.background(LocalColors.current.gray300)
        ) {
            MySwitch(
                checked = true,
                scale = 0.8f
            )
        }

        Box (
            modifier = Modifier.background(LocalColors.current.gray300)
        ) {
            MySwitch(
                checked = true,
                scale = 1f
            )
        }


    }

}