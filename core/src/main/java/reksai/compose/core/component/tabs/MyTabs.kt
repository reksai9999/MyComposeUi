package reksai.compose.core.component.tabs

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 一般用于顶部top 的 Tab标签(不可滚动)
 */
@Composable
fun <T> MyTabs(
    tabs: List<T>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    divider: @Composable () -> Unit = @Composable { },
    indicatorColor: Color = Color.Red,
    indicator: @Composable TabIndicatorScope.() -> Unit =
        @Composable {
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = false),
                color = indicatorColor,
                height = 2.5.dp
            )
        },
    content: @Composable (index: Int, item: T, isSelected: Boolean) -> Unit,
) {
    Box(modifier = modifier) {
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            indicator = indicator,
            divider = divider,
        ) {
            tabs.forEachIndexed { index, item ->
                content(index, item, index == selectedTabIndex)
            }
        }
    }
}

/**
 * 一般用于顶部top 的 Tab标签(可滚动)
 */
@Composable
fun <T> MyTabsScrollable(
    tabs: List<T>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    divider: @Composable () -> Unit = @Composable { },
    indicatorColor: Color = Color.Red,
    indicator: @Composable TabIndicatorScope.() -> Unit =
        @Composable {
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = false),
                color = indicatorColor,
                height = 2.5.dp
            )
        },
    edgePadding: Dp = 0.dp,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable (index: Int, item: T, isSelected: Boolean) -> Unit,
) {
    Box(modifier = modifier) {
        SecondaryScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            indicator = indicator,
            divider = divider,
            edgePadding = edgePadding,
            scrollState = scrollState,
        ) {
            tabs.forEachIndexed { index, item ->
                content(index, item, index == selectedTabIndex)
            }
        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyhTabsPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        MyTabs(
            tabs = listOf("标签一", "标签二", "标签三", "标签四", "标签五"),
            selectedTabIndex = 0,
            modifier = Modifier,
            indicatorColor = Color.Red,
        ) { index, title, isSelected ->
            Text(
                text = title,
                color = if (isSelected) Color.Red else Color.Black,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        MyTabsScrollable(
            tabs = listOf("滚动标签一", "滚动标签二", "滚动标签三", "滚动标签四", "滚动标签五", "滚动标签六", "滚动标签七", "滚动标签八"),
            selectedTabIndex = 4,
        ) { index, title, isSelected ->
            Text(
                text = title,
                color = if (isSelected) Color.Red else Color.Black,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }

}