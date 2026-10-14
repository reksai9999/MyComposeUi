package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.roundToIntRect
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyMenu
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography
import reksai.compose.core.theme.MyTheme

private val menuList = List(5) { "选项 ${it + 1}" }

/** 示例页面入口，沿用现有 Menu 导航。 */
@Composable
fun MenuScreenVM() {
    MenuScreen(Modifier.fillMaxSize())
}

/** 通过 100 项列表把同一个按钮滚动到不同位置，检查菜单的上下展开行为。 */
@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    Column(modifier.background(LocalColors.current.background)) {
        MyTopBar(title = "Menu 列表定位")
        Text(
            text = "共 100 项，将任意一项滚动到顶部或底部，再点击右侧菜单。",
            style = LocalTypography.current.bodySmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            items(count = 100, key = { it + 1 }) { index ->
                MenuExampleRow(index + 1)
                if (index < 99) HorizontalDivider()
            }
        }
    }
}

/** 每行保留自己的选中项；稳定的列表 key 让滚出屏幕后再返回时仍对应原来的行。 */
@Composable
private fun MenuExampleRow(number: Int) {
    var selected by rememberSaveable { mutableStateOf("选项 2") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("第 $number 项", style = LocalTypography.current.bodyMedium)
            Text("已选择：$selected", style = LocalTypography.current.bodySmall)
        }
        MenuExampleButton(
            title = "菜单",
            items = menuList,
            selected = selected,
            modifier = Modifier.semantics { contentDescription = "第 $number 项的菜单" },
            onSelect = { selected = it },
        )
    }
}

/** 按钮负责采集自己的边界，菜单即使放在大容器中也始终贴着这个按钮。 */
@Composable
private fun MenuExampleButton(
    title: String,
    items: List<String>,
    selected: String,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit,
) {
    var show by remember { mutableStateOf(false) }
    var anchorBounds by remember { mutableStateOf<IntRect?>(null) }
    Box(modifier) {
        MyFillButton(
            text = title,
            modifier = Modifier.onGloballyPositioned {
                anchorBounds = it.boundsInWindow().roundToIntRect()
            },
            onClick = { show = true },
        )
        MyMenu(
            show = show && anchorBounds != null,
            onHide = { show = false },
            selected = selected,
            list = items,
            anchorBounds = anchorBounds,
            modifier = Modifier
                .width(220.dp)
                .background(LocalColors.current.blackOpacity80, LocalShapes.current.medium),
        ) { item, _, isSelected ->
            Text(
                text = if (isSelected) "$item ✓" else item,
                style = LocalTypography.current.bodyMedium,
                color = if (isSelected) LocalColors.current.yellow else LocalColors.current.white200,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelect(item)
                        show = false
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }
    }
}

/** 完整示例页可使用交互预览点击按钮，另提供矮屏布局方便检查。 */
@Preview(name = "菜单示例", widthDp = 393, heightDp = 800, showBackground = true)
@Preview(name = "菜单示例 · 矮屏", widthDp = 360, heightDp = 480, showBackground = true)
@Composable
private fun MenuScreenPreview() {
    MyTheme { MenuScreen(Modifier.fillMaxSize()) }
}
