package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.tabs.MyTabs
import reksai.compose.core.component.tabs.MyTabsScrollable
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors

@Composable
fun TabsScreen(
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Tabs",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.white200)
        ) {
            val tabs = listOf("标签一", "标签二", "标签三", "标签四", "标签五")
            val state = rememberPagerState() { tabs.size }
            val state2 = rememberPagerState() { tabs.size }

            Column {
                MyTabs(
                    tabs = tabs,
                    selectedTabIndex = state.currentPage,
                    indicatorColor = Color.Red,
                ) { index, title, isSelected ->
                    Text(
                        text = title,
                        color = if (isSelected) Color.Red else Color.Black,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 8.dp)
                            .clickableNormalNoEffect {
                                // 点击tab 切换页面
                                scope.launch {
                                    state.animateScrollToPage(index)
                                }
                            }
                    )
                }
                HorizontalPager(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { page ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(LocalColors.current.background)
                    ) {
                        Text(
                            text = "标签${page + 1}的内容",
                            modifier = Modifier
                        )
                    }
                }

            }




            Column {
                MyTabsScrollable(
                    tabs = tabs.map { "滚动$it" },
                    selectedTabIndex = state2.currentPage,
                ) { index, title, isSelected ->
                    Text(
                        text = title,
                        color = if (isSelected) Color.Red else Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickableNormalNoEffect {
                                // 点击tab 切换页面
                                scope.launch {
                                    state2.animateScrollToPage(index)
                                }
                            }
                    )
                }
                HorizontalPager(
                    state = state2,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { page ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(LocalColors.current.background)
                    ) {
                        Text(
                            text = "标签${page + 1}的内容",
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }

            }

        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun TabsScreenPreview() {
    TabsScreen(
        modifier = Modifier.fillMaxSize()
    )
}