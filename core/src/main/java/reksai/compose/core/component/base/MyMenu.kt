package reksai.compose.core.component.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun <T> MyMenu(
    show: Boolean,
    onHide: () -> Unit,
    selected: T,
    list: List<T>,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    vertical: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(5.dp),
    itemContent: @Composable (
        item: T,
        index: Int,
        isSelected: Boolean,
    ) -> Unit = { _, _, _ -> },
) {
    DropdownMenu(
        expanded = show,
        onDismissRequest = onHide,
        offset = offset,
        containerColor = LocalColors.current.transparent,
        shape = LocalShapes.current.none,
        border = BorderStroke(0.dp, LocalColors.current.transparent),
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = vertical,
            modifier = Modifier
        ) {
            list.forEachIndexed { index, item ->
                itemContent(item, index, selected == item)
            }
        }
    }
}

/**
 * 简化版菜单 不需要选中项, 只显示一些提示信息的时候用的
 */
@Composable
fun MyMenu(
    modifier: Modifier = Modifier,
    show: Boolean,
    onHide: () -> Unit,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    content: @Composable () -> Unit
) {
    MyMenu(
        show = show,
        onHide = onHide,
        selected = Any(),
        list = listOf(Any()),
        modifier = modifier,
        offset = offset,
        itemContent = { _, _, _ -> content() }
    )
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyMenuTipPreview() {
    val showTip = true
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.background)
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MyMenu(
            show = showTip,
            onHide = {},
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .background(LocalColors.current.blackOpacity60, shape = LocalShapes.current.medium)
                    .padding(15.dp)
            ) {
                Text(
                    text = "这是一个提示内容, 简单的提示信息展示.",
                    style = LocalTypography.current.bodySmall,
                    color = LocalColors.current.white200,
                    modifier = Modifier
                )
            }
        }

        MyMenu(
            show = showTip,
            onHide = {},
            selected = "选项3",
            list = listOf("选项1", "选项2", "选项3", "选项4", "选项5555555555"),
            modifier = Modifier
                .padding(top = 80.dp)
                .weight(1f)
                .background(LocalColors.current.blackOpacity60)
        ) { item, index, isSelected ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalColors.current.blackOpacity60, shape = LocalShapes.current.medium)
                    .padding(15.dp)
            ) {
                Text(
                    text = item,
                    style = LocalTypography.current.bodySmall,
                    color = if (isSelected) LocalColors.current.yellow else LocalColors.current.white200,
                    modifier = Modifier
                )
            }
        }
    }
}

