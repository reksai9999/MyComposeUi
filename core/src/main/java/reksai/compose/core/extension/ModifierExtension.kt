package reksai.compose.core.extension

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.apply
import kotlin.let
import kotlin.run

/**
 * 软键盘隐藏时清除焦点的修饰符
 */
@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    var isProgrammaticFocus by remember { mutableStateOf(false) }

    val imeIsVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    if (isFocused) {
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
                // 键盘出现后重置程序焦点标志
                isProgrammaticFocus = false
            } else if (keyboardAppearedSinceLastFocused && !isProgrammaticFocus) {
                focusManager.clearFocus()
            }
        }
    }

    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
                // 使用当前的键盘状态而不是重新访问
                isProgrammaticFocus = !imeIsVisible
            }
        }
    }
}

/**
 * 滚动到指定的 BringIntoViewRequester
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.scrollBringIntoView(bringIntoViewRequester: BringIntoViewRequester): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()

    bringIntoViewRequester(bringIntoViewRequester)
    onFocusChanged {
        if (it.isFocused) {
            coroutineScope.launch {
                delay(400)
                bringIntoViewRequester.bringIntoView()
            }
        }
    }
}

/**
 * 点击事件修饰符，使用默认的Ripple效果
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableNormal(onClick : () -> Unit) = composed {
    this.clickable(
        interactionSource =remember { MutableInteractionSource() },
        indication = ripple(),
        onClick = onClick
    )
}

/**
 * 点击事件修饰符，使用默认的Ripple效果，但不显示点击效果
 */
fun Modifier.clickableNormalNoEffect(onClick : () -> Unit) = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}

/**
 * 绘制9-patch图片的修饰符
 */
fun Modifier.draw9Patch(
    context: Context,
    @DrawableRes ninePatchRes: Int,
) = this.drawBehind {
    drawIntoCanvas {
        ContextCompat.getDrawable(context, ninePatchRes)?.let { ninePatch ->
            ninePatch.run {
                bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                draw(it.nativeCanvas)
            }
        }
    }
}

/**
 * 绘制阴影的修饰符
 */
fun Modifier.dropShadow(
    shape: Shape = RoundedCornerShape(0.dp),
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

/**
 * 双重阴影效果的修饰符
 */
fun Modifier.doubleShadowDrop(
    shape: Shape,
    offset: Dp = 4.dp,
    blur: Dp = 8.dp
) = this
    .dropShadow(shape, Color.Black.copy(0.25f), blur, offset, offset)
    .dropShadow(shape, Color.White.copy(0.25f), blur, -offset, -offset)

/**
 * 自定义边框的修饰符
 * @param width 边框宽度
 * @param color 边框颜色
 * @param showTop 是否显示上边框
 * @param showBottom 是否显示下边框
 * @param showLeft 是否显示左边框
 * @param showRight 是否显示右边框
 */
fun Modifier.borderCustom(
    width: Dp,
    color: Color,
    showTop: Boolean = false,
    showBottom: Boolean = false,
    showLeft: Boolean = false,
    showRight: Boolean = false
) = this.drawWithContent {
    drawContent()

    val strokeWidth = width.toPx()
    val halfStrokeWidth = strokeWidth / 2

    // 绘制上边框
    if (showTop) {
        drawLine(
            color = color,
            start = Offset(0f, halfStrokeWidth),
            end = Offset(size.width, halfStrokeWidth),
            strokeWidth = strokeWidth
        )
    }

    // 绘制下边框
    if (showBottom) {
        drawLine(
            color = color,
            start = Offset(0f, size.height - halfStrokeWidth),
            end = Offset(size.width, size.height - halfStrokeWidth),
            strokeWidth = strokeWidth
        )
    }

    // 绘制左边框
    if (showLeft) {
        drawLine(
            color = color,
            start = Offset(halfStrokeWidth, 0f),
            end = Offset(halfStrokeWidth, size.height),
            strokeWidth = strokeWidth
        )
    }

    // 绘制右边框
    if (showRight) {
        drawLine(
            color = color,
            start = Offset(size.width - halfStrokeWidth, 0f),
            end = Offset(size.width - halfStrokeWidth, size.height),
            strokeWidth = strokeWidth
        )
    }
}

/**
 * 自定义边框的修饰符，支持单独设置每个边框的宽度和颜色
 */
fun Modifier.borderCustom(
    topWidth: Dp = 0.dp,
    topColor: Color = Color.Transparent,
    bottomWidth: Dp = 0.dp,
    bottomColor: Color = Color.Transparent,
    leftWidth: Dp = 0.dp,
    leftColor: Color = Color.Transparent,
    rightWidth: Dp = 0.dp,
    rightColor: Color = Color.Transparent
) = this.drawWithContent {
    drawContent()

    // 绘制上边框
    if (topWidth > 0.dp) {
        val strokeWidth = topWidth.toPx()
        drawLine(
            color = topColor,
            start = Offset(0f, strokeWidth / 2),
            end = Offset(size.width, strokeWidth / 2),
            strokeWidth = strokeWidth
        )
    }

    // 绘制下边框
    if (bottomWidth > 0.dp) {
        val strokeWidth = bottomWidth.toPx()
        drawLine(
            color = bottomColor,
            start = Offset(0f, size.height - strokeWidth / 2),
            end = Offset(size.width, size.height - strokeWidth / 2),
            strokeWidth = strokeWidth
        )
    }

    // 绘制左边框
    if (leftWidth > 0.dp) {
        val strokeWidth = leftWidth.toPx()
        drawLine(
            color = leftColor,
            start = Offset(strokeWidth / 2, 0f),
            end = Offset(strokeWidth / 2, size.height),
            strokeWidth = strokeWidth
        )
    }

    // 绘制右边框
    if (rightWidth > 0.dp) {
        val strokeWidth = rightWidth.toPx()
        drawLine(
            color = rightColor,
            start = Offset(size.width - strokeWidth / 2, 0f),
            end = Offset(size.width - strokeWidth / 2, size.height),
            strokeWidth = strokeWidth
        )
    }
}

/**
 * 增强版虚线边框 Modifier
 *
 * @param width 边框宽度
 * @param color 边框颜色
 * @param dashWidth 虚线段长度
 * @param dashGap 虚线间隔长度
 * @param cornerRadius 圆角半径
 */
fun Modifier.dashedBorder(
    width: Dp = 1.dp,
    color: Color = Color.Black,
    dashWidth: Dp = 8.dp,
    dashGap: Dp = 4.dp,
    cornerRadius: Dp = 0.dp,
): Modifier = this.then(
    Modifier.drawWithContent {
        drawContent()

        val strokeWidth = width.toPx()
        val dashWidthPx = dashWidth.toPx()
        val dashGapPx = dashGap.toPx()
        val cornerRadiusPx = cornerRadius.toPx()

        val pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidthPx, dashGapPx),
            phase = 0f
        )

        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(cornerRadiusPx),
            style = Stroke(
                width = strokeWidth,
                pathEffect = pathEffect
            )
        )
    }
)











