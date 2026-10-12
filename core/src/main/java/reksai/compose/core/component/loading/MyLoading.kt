package reksai.compose.core.component.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MyLoading(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    text: String = "",
    backgroundColor: Color = LocalColors.current.blackOpacity10,
    loadingBackgroundColor: Color = LocalColors.current.blackOpacity70,
    loadingColor: Color = LocalColors.current.white200,
    textColor: Color = LocalColors.current.white200,
    textStyle: TextStyle = LocalTypography.current.titleSmall,
    loadingContent: @Composable AnimatedVisibilityScope.() -> Unit = {
        LoadingContentNormal(
            modifier = Modifier.fillMaxSize(),
            text = text,
            backgroundColor = backgroundColor,
            loadingBackgroundColor = loadingBackgroundColor,
            loadingColor = loadingColor,
            textColor = textColor,
            textStyle = textStyle
        )
    },
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {

        content()

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            loadingContent()
        }
    }
}

@Composable
fun LoadingContentNormal(
    modifier: Modifier = Modifier,
    text: String = "",
    backgroundColor: Color = LocalColors.current.blackOpacity10,
    loadingBackgroundColor: Color = LocalColors.current.blackOpacity70,
    loadingColor: Color = LocalColors.current.white200,
    textColor: Color = LocalColors.current.white200,
    textStyle: TextStyle = LocalTypography.current.titleSmall,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(backgroundColor)
            .clickableNormalNoEffect { }
    )
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .background(color = loadingBackgroundColor, shape = LocalShapes.current.medium)
                .padding(horizontal = 40.dp, vertical = 25.dp)
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(50.dp)
            ) {
                LineSpinFadeLoaderIndicator(
                    color = loadingColor,
                    rectCount = 10,
                    penThickness = 10f,
                    radius = 40f,
                    elementHeight = 15f,
                )
            }

            if (text.isNotEmpty()) {
                Text(
                    text = text,
                    style = textStyle,
                    color = textColor,
                )
            }
        }

    }
}

@Composable
fun LineSpinFadeLoaderIndicator(
    color: Color = Color.White,
    rectCount: Int = 8,
    linearAnimationType: LinearAnimationType = LinearAnimationType.CIRCULAR,
    penThickness: Float = 25f,
    radius: Float = 55f,
    elementHeight: Float = 20f,
    minAlpha: Float = 0.2f,
    maxAlpha: Float = 1.0f
) {

    val angleStep = 360f / rectCount
    val outerRadius = radius + elementHeight
    val innerRadius = radius


// ------------------------ scale animation ---------------------
    val alphas = (1..rectCount).map { index ->
        var alpha: Float by remember { mutableStateOf(minAlpha) }
        LaunchedEffect(key1 = Unit) {

            when (linearAnimationType) {
                LinearAnimationType.CIRCULAR -> {
                    delay(linearAnimationType.circleDelay * index)
                }

                LinearAnimationType.SKIP_AND_REPEAT -> {
                    delay(linearAnimationType.circleDelay * index) // The constant value, here 250L, must be the same animation duration for this pattern to run
                }
            }

            animate(
                initialValue = minAlpha,
                targetValue = maxAlpha,
                animationSpec = infiniteRepeatable(
                    animation = when (linearAnimationType) {
                        LinearAnimationType.CIRCULAR -> {
                            tween(durationMillis = linearAnimationType.animDuration)
                        }

                        LinearAnimationType.SKIP_AND_REPEAT -> {
                            tween(durationMillis = linearAnimationType.animDuration)
                        }
                    },
                    repeatMode = RepeatMode.Reverse,
                )
            ) { value, _ -> alpha = value }
        }

        alpha
    }


// ----------------------------- UI --------------------------

    Canvas(modifier = Modifier) {

        val center = Offset(size.width / 2, size.height / 2)

        for (index in 0 until rectCount) {

            val angle = index * angleStep

            val startX =
                center.x + innerRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val startY =
                center.y + innerRadius * sin(Math.toRadians(angle.toDouble())).toFloat()

            val endX = center.x + outerRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val endY = center.y + outerRadius * sin(Math.toRadians(angle.toDouble())).toFloat()

            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = penThickness * alphas[index],
                alpha = alphas[index],
                cap = StrokeCap.Round,
            )
        }
    }
}

enum class LinearAnimationType(val animDuration: Int, val circleDelay: Long) {
    CIRCULAR(500, 100L),
    SKIP_AND_REPEAT(250, 250L);
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun LoadingContentPreview() {
    LoadingContentNormal(
        text = "加载中...",
        modifier = Modifier.fillMaxSize(),
    )
}