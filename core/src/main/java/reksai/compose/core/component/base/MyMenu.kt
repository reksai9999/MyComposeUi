package reksai.compose.core.component.base

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.roundToIntRect
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography
import reksai.compose.core.theme.MyTheme

/**
 * 根据点击控件的位置展开菜单：下方优先，其次上方，两侧都不足时向下限高滚动。
 *
 * [anchorBounds] 是点击控件在窗口中的像素边界，可在控件的 onGloballyPositioned 中
 * 通过 boundsInWindow().roundToIntRect() 获取。未传入时使用菜单所在父布局的边界，
 * 此时应把按钮与菜单放在同一个包裹内容大小的 Box 中，避免大容器影响定位。
 * [offset] 的正向 y 值表示与控件的间隔，向上展开时会从控件顶部减去该间隔。
 * [modifier] 只修饰菜单内容；显示及选中状态仍由调用方管理。
 */
@Composable
fun <T> MyMenu(
    show: Boolean,
    onHide: () -> Unit,
    selected: T,
    list: List<T>,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    clippingEnabled: Boolean = false,
    vertical: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(5.dp),
    anchorBounds: IntRect? = null,
    itemContent: @Composable (item: T, index: Int, isSelected: Boolean) -> Unit = { _, _, _ -> },
) {
    if (!show) return

    var parentBounds by remember { mutableStateOf<IntRect?>(null) }
    if (anchorBounds == null) {
        // 与 Popup 一样读取直接父布局，不额外包一层 Box 改变旧调用的锚点。
        Layout(
            content = {},
            modifier = Modifier.onGloballyPositioned { coordinates ->
                coordinates.parentLayoutCoordinates?.let { parent ->
                    parentBounds = IntRect(parent.positionInWindow().round(), parent.size)
                }
            },
        ) { _, _ -> layout(0, 0) {} }
    }
    val anchor = anchorBounds ?: parentBounds ?: return
    val visibleBounds = menuVisibleWindowBounds()
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val offsetPx = with(density) { IntOffset(offset.x.roundToPx(), offset.y.roundToPx()) }
    val positionProvider = remember(anchor, visibleBounds, offsetPx, layoutDirection) {
        MyMenuPositionProvider(anchor, visibleBounds, offsetPx, layoutDirection)
    }

    Popup(
        popupPositionProvider = positionProvider,
        onDismissRequest = onHide,
        properties = PopupProperties(focusable = true, clippingEnabled = clippingEnabled),
    ) {
        MeasuredMenu(positionProvider, modifier, vertical) {
            list.forEachIndexed { index, item -> itemContent(item, index, selected == item) }
        }
    }
}

/** 简化版菜单：不需要选中项时使用，定位规则与列表菜单完全一致。 */
@Composable
fun MyMenu(
    modifier: Modifier = Modifier,
    show: Boolean,
    onHide: () -> Unit,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    clippingEnabled: Boolean = false,
    anchorBounds: IntRect? = null,
    content: @Composable () -> Unit,
) {
    MyMenu(
        show = show,
        onHide = onHide,
        selected = Unit,
        list = listOf(Unit),
        modifier = modifier,
        offset = offset,
        clippingEnabled = clippingEnabled,
        anchorBounds = anchorBounds,
        itemContent = { _, _, _ -> content() },
    )
}

/** 合并 Insets 和窗口可见区域，兼容已由宿主消费系统栏 Insets 的页面及预览。 */
@Composable
private fun menuVisibleWindowBounds(): IntRect {
    val density = LocalDensity.current
    val direction = LocalLayoutDirection.current
    val windowSize = LocalWindowInfo.current.containerSize
    // 某些预览宿主尚未提供窗口大小，此时使用预览配置中的尺寸。
    val configuration = LocalConfiguration.current
    val width = windowSize.width.takeIf { it > 0 }
        ?: with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val height = windowSize.height.takeIf { it > 0 }
        ?: with(density) { configuration.screenHeightDp.dp.roundToPx() }
    val insets = WindowInsets.safeDrawing
    val view = LocalView.current
    var visibleFrame by remember(view) { mutableStateOf(readMenuVisibleFrame(view)) }
    DisposableEffect(view) {
        val observer = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            visibleFrame = readMenuVisibleFrame(view)
        }
        observer.addOnGlobalLayoutListener(listener)
        visibleFrame = readMenuVisibleFrame(view)
        onDispose {
            if (observer.isAlive) observer.removeOnGlobalLayoutListener(listener)
        }
    }
    val frame = visibleFrame ?: IntRect(0, 0, width, height)
    val left = maxOf(insets.getLeft(density, direction), frame.left).coerceIn(0, width)
    val top = maxOf(insets.getTop(density), frame.top).coerceIn(0, height)
    return IntRect(
        left = left,
        top = top,
        right = minOf(width - insets.getRight(density, direction), frame.right).coerceIn(left, width),
        bottom = minOf(height - insets.getBottom(density), frame.bottom).coerceIn(top, height),
    )
}

/** 将系统返回的屏幕可见区域转换成窗口坐标，避免分屏或浮动窗口产生坐标偏移。 */
private fun readMenuVisibleFrame(view: View): IntRect? {
    val frame = Rect()
    view.getWindowVisibleDisplayFrame(frame)
    if (frame.isEmpty) return null
    val onScreen = IntArray(2)
    val inWindow = IntArray(2)
    view.getLocationOnScreen(onScreen)
    view.getLocationInWindow(inWindow)
    val dx = onScreen[0] - inWindow[0]
    val dy = onScreen[1] - inWindow[1]
    return IntRect(frame.left - dx, frame.top - dy, frame.right - dx, frame.bottom - dy)
}

/**
 * 先读取固定宽度下的完整期望高度，再一次性测量滚动视口，避免先限高再判断方向。
 * 与 DropdownMenu 一样使用固有尺寸确定宽度，内容应使用普通布局而非 LazyColumn。
 */
@Composable
private fun MeasuredMenu(
    positionProvider: MyMenuPositionProvider,
    modifier: Modifier,
    vertical: Arrangement.HorizontalOrVertical,
    content: @Composable () -> Unit,
) {
    val scrollState = rememberScrollState()
    Layout(
        content = {
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .padding(vertical = 8.dp),
                verticalArrangement = vertical,
            ) { content() }
        },
    ) { measurables, constraints ->
        val measurable = measurables.single()
        val maxWidth = minOf(constraints.maxWidth, positionProvider.visibleBounds.width)
        val width = measurable.maxIntrinsicWidth(Constraints.Infinity).coerceIn(0, maxWidth)
        val desiredHeight = measurable.maxIntrinsicHeight(width).coerceAtLeast(0)
        positionProvider.desiredSize = IntSize(width, desiredHeight)
        val placement = positionProvider.placement()
        val placeable = measurable.measure(
            Constraints(
                minWidth = width,
                maxWidth = width,
                maxHeight = minOf(placement.height, constraints.maxHeight),
            ),
        )
        layout(placeable.width, placeable.height) { placeable.place(0, 0) }
    }
}

/** 将完整期望尺寸保留给 Popup，不能使用裁剪后的 popupContentSize 重新选择方向。 */
private class MyMenuPositionProvider(
    private val anchorBounds: IntRect,
    val visibleBounds: IntRect,
    private val offset: IntOffset,
    private val layoutDirection: LayoutDirection,
) : PopupPositionProvider {
    var desiredSize by mutableStateOf(IntSize.Zero)

    /** 测量和窗口定位共用同一份计算结果。 */
    fun placement(): MyMenuPlacement = calculateMyMenuPlacement(
        anchorBounds, visibleBounds, desiredSize, offset, layoutDirection,
    )

    /** 显式锚点与安全区域均为窗口坐标，不受 Popup 的父容器大小影响。 */
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset = placement().position
}

/** 菜单最终位置与滚动视口高度，均使用窗口中的像素值。 */
internal data class MyMenuPlacement(val position: IntOffset, val height: Int, val opensDown: Boolean)

/** 按完整高度选择下方、上方或下方滚动，并将水平方向限制在可见窗口内。 */
internal fun calculateMyMenuPlacement(
    anchorBounds: IntRect,
    visibleBounds: IntRect,
    desiredSize: IntSize,
    offset: IntOffset = IntOffset.Zero,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): MyMenuPlacement {
    val belowStart = (anchorBounds.bottom + offset.y).coerceIn(visibleBounds.top, visibleBounds.bottom)
    val aboveEnd = (anchorBounds.top - offset.y).coerceIn(visibleBounds.top, visibleBounds.bottom)
    val belowSpace = visibleBounds.bottom - belowStart
    val aboveSpace = aboveEnd - visibleBounds.top
    val opensDown = desiredSize.height <= belowSpace || desiredSize.height > aboveSpace
    val height = desiredSize.height.coerceIn(0, if (opensDown) belowSpace else aboveSpace)
    val width = desiredSize.width.coerceIn(0, visibleBounds.width)
    val preferredX = if (layoutDirection == LayoutDirection.Ltr) {
        anchorBounds.left + offset.x
    } else {
        anchorBounds.right - width - offset.x
    }
    val x = preferredX.coerceIn(visibleBounds.left, visibleBounds.right - width)
    return MyMenuPlacement(
        position = IntOffset(x, if (opensDown) belowStart else aboveEnd - height),
        height = height,
        opensDown = opensDown,
    )
}

/** 默认展开的预览共用实际组件，按钮边界与运行时使用相同的采集方式。 */
@Composable
private fun MyMenuPlacementPreview(alignment: Alignment, longMenu: Boolean = false) {
    MyTheme {
        var anchor by remember { mutableStateOf<IntRect?>(null) }
        Box(Modifier
            .fillMaxSize()
            .background(LocalColors.current.background)
            .padding(24.dp)) {
            MyFillButton(
                text = if (longMenu) "两侧不足，向下滚动" else "菜单锚点",
                modifier = Modifier
                    .align(alignment)
                    .onGloballyPositioned {
                        anchor = it.boundsInWindow().roundToIntRect()
                    },
            )
            MyMenu(
                show = anchor != null,
                onHide = {},
                selected = "选项 2",
                list = List(if (longMenu) 30 else 4) { "选项 ${it + 1}" },
                anchorBounds = anchor,
                modifier = Modifier
                    .width(200.dp)
                    .background(LocalColors.current.blackOpacity80, LocalShapes.current.medium),
            ) { item, _, selected ->
                Text(
                    text = item,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    color = if (selected) LocalColors.current.yellow else LocalColors.current.white200,
                    style = LocalTypography.current.bodySmall,
                )
            }
        }
    }
}

/** 顶部控件：下方足够时向下展开。 */
@Preview(name = "向下展开", widthDp = 393, heightDp = 800, showBackground = true)
@Composable
private fun MyMenuDownPreview() = MyMenuPlacementPreview(Alignment.TopStart)

/** 底部控件：下方不足时向上展开。 */
@Preview(name = "向上展开", widthDp = 393, heightDp = 800, showBackground = true)
@Composable
private fun MyMenuUpPreview() = MyMenuPlacementPreview(Alignment.BottomEnd)

/** 较矮窗口与超长菜单：两侧都不足时仍向下展开并滚动。 */
@Preview(name = "矮屏长菜单向下滚动", widthDp = 360, heightDp = 480, showBackground = true)
@Composable
private fun MyMenuLongPreview() = MyMenuPlacementPreview(Alignment.Center, longMenu = true)
