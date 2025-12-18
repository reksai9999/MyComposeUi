package reksai.compose.core.component.pager

import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun <T> MyPager(
    items: List<T>,
    modifier: Modifier = Modifier,
    initPageIndex: Int = 0,
    loop: Boolean = false,
    autoScroll: Boolean = false,
    autoScrollDuration: Long = 3000L,
    pagerState: PagerState = rememberPagerState(pageCount = {
        if (items.isEmpty()) 0 else if (loop && items.size > 1) Int.MAX_VALUE else items.size
    }),
    indicatorOffset: DpOffset = DpOffset(0.dp, 0.dp),
    indicator: @Composable BoxScope.(actualCurrentPage: Int, actualTotalPage: Int, pagerState: PagerState) -> Unit = { actualCurrentPage, totalPage, pagerState ->
        DefaultBallPagerIndicator(
            page = actualCurrentPage,
            totalPage = totalPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(indicatorOffset.x, indicatorOffset.y)
        )
    },
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSpacing: Dp = 15.dp,
    itemContent: @Composable (item: T, pagerState: PagerState) -> Unit,
) {
    if (items.isEmpty()) return

    // 是否正在拖拽
    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // 真实的项目数量
    val actualItemCount = items.size
    val startIndex = Int.MAX_VALUE / 2

    // 计算实际当前页面索引
    val actualCurrentPage by remember {
        derivedStateOf {
            if (loop && actualItemCount > 1) {
                pagerState.currentPage % actualItemCount
            } else {
                pagerState.currentPage.coerceIn(0, actualItemCount - 1)
            }
        }
    }

    // 使用 rememberSaveable 保存当前实际页面索引，而不是初始页面索引
    val savedCurrentPage = rememberSaveable { mutableIntStateOf(initPageIndex) }

    // 初始化到指定页面（只在第一次创建时执行）
    LaunchedEffect(items) {
        pagerState.scrollToPage(savedCurrentPage.intValue)
    }

    // 监听页面变化，保存当前实际页面索引
    LaunchedEffect(actualCurrentPage) {
        savedCurrentPage.intValue = actualCurrentPage
    }

    if (!pagerIsDragged) {
        // 自动滚动功能
        LaunchedEffect(autoScroll, autoScrollDuration, actualItemCount) {
            if (autoScroll && actualItemCount > 1) {
                while (true) {
                    delay(autoScrollDuration)
                    val nextPage = if (loop) {
                        pagerState.currentPage + 1
                    } else {
                        // 非循环模式下，到达最后一页时回到第一页
                        val currentIndex = pagerState.currentPage
                        if (currentIndex >= actualItemCount - 1) {
                            0
                        } else {
                            currentIndex + 1
                        }
                    }
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }


    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            contentPadding = contentPadding,
            pageSpacing = pageSpacing,
            modifier = Modifier
        ) { page ->
            val actualIndex = if (loop && actualItemCount > 1) {
                // 确保索引始终为正数
                ((page % actualItemCount) + actualItemCount) % actualItemCount
            } else {
                page.coerceIn(0, actualItemCount - 1)
            }
            itemContent(items[actualIndex], pagerState)
        }

        // 指示器，传递实际当前页面
        indicator(actualCurrentPage, actualItemCount, pagerState)
    }
}

/**
 * 获取实际的当前页面索引（用于指示器显示）
 */
@Composable
fun PagerState.getCurrentPageIndex(itemCount: Int, loop: Boolean): Int {
    return if (loop && itemCount > 1) {
        ((currentPage % itemCount) + itemCount) % itemCount
    } else {
        currentPage.coerceIn(0, itemCount - 1)
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyPagerPreview() {
    MyPager(
        items = listOf(1, 2, 3, 4, 5),
        initPageIndex = 2
    ) { item, pagerState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}