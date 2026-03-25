package reksai.compose.core.component.selector

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties

@Composable
fun <T> MyDropdown(
    list: List<T>,
    defaultValue: T? = null,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    dropdownModifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    shape: Shape = MaterialTheme.shapes.extraSmall,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    tonalElevation: Dp = MenuDefaults.TonalElevation,
    shadowElevation: Dp = MenuDefaults.ShadowElevation,
    border: BorderStroke? = null,
    anchorContent: @Composable (selectedItem: T?, isExpanded: Boolean) -> Unit,
    itemContent: @Composable (item: T, isSelected: Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(defaultValue) }
    val density = LocalDensity.current

    // 获取导航栏高度像素
    val navInsets = WindowInsets.navigationBars
    val navBarHeightPx = navInsets.getBottom(density)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier.clickable(
                onClick = { expanded = true },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
        ) {
            anchorContent(selectedItem, expanded)
        }

        if (expanded) {
            Popup(
                onDismissRequest = { expanded = false },
                properties = properties,
                popupPositionProvider = remember(offset, density, navBarHeightPx) {
                    MyDropdownPositionProvider(offset, density, navBarHeightPx)
                }
            ) {
                Surface(
                    shape = shape,
                    color = containerColor,
                    tonalElevation = tonalElevation,
                    shadowElevation = shadowElevation,
                    border = border,
                    modifier = Modifier.width(IntrinsicSize.Max)
                ) {
                    Column(
                        modifier = dropdownModifier.verticalScroll(rememberScrollState())
                    ) {
                        list.forEach { item ->
                            val isSelected = item == selectedItem
                            Box(
                                modifier = Modifier.clickable(
                                    onClick = {
                                        selectedItem = item
                                        expanded = false
                                        onItemSelected(item)
                                    },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                )
                            ) {
                                itemContent(item, isSelected)
                            }
                        }
                    }
                }
            }
        }
    }
}

private class MyDropdownPositionProvider(
    val contentOffset: DpOffset,
    val density: Density,
    val navBarHeightPx: Int
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val xOffset = with(density) { contentOffset.x.roundToPx() }
        val yOffset = with(density) { contentOffset.y.roundToPx() }

        val x = anchorBounds.left + xOffset

        // 核心判断逻辑
        // 1. windowSize.height 在 clippingEnabled = false 时通常代表物理屏幕总高度
        // 2. 根据用户测试，使用 + navBarHeightPx 能达到最理想的自动切换边界效果
        val limitY = windowSize.height + navBarHeightPx

        // 3. 计算如果向下弹，弹出框底部的绝对 Y 坐标
        val popupBottomYIfDown = anchorBounds.bottom + popupContentSize.height + yOffset

        // 4. 只有当弹出框底部“真的超过了”计算出的边界，才向上弹
        // 否则（即便只差 1 像素），也坚持向下弹
        val showBelow = popupBottomYIfDown <= limitY

        val y = if (showBelow) {
            anchorBounds.bottom + yOffset
        } else {
            anchorBounds.top - popupContentSize.height - yOffset
        }

        return IntOffset(x, y)
    }
}
