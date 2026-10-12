package reksai.compose.core.component.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import reksai.compose.core.component.paging.enums.GridListType
import reksai.compose.core.component.paging.loading.MyBottomLoadingBox
import reksai.compose.core.component.paging.loading.MyGridLoadingBox
import reksai.compose.core.component.paging.loading.MyListLoadingBox

/**
 * 支持列表、网格、下拉刷新及分页状态插槽的 Paging 组件。
 * 布局选择、数据和滚动状态由调用方管理；切换布局时同步首个可见项的位置。
 * [key] 对应分页数据索引，不包含头部插槽；需要稳定业务键时可传 pagingItems.itemKey { it.id }。
 * [itemContent] 必须渲染有尺寸的内容，避免空条目持续触发下一页请求。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> MyPaging(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    gridListType: GridListType = GridListType.List,
    gridCellCount: Int = 2,
    contentItemsBefore: (@Composable () -> Unit)? = null,
    headerContent: (@Composable () -> Unit)? = null,

    loadErrorContent: @Composable ((refresh: () -> Unit) -> Unit)? = { refresh ->
        MyPagingErrorBox(text = "데이터를 불러오지 못했습니다.", onRetry = refresh)
    },
    loadingContent: @Composable (GridListType) -> Unit = {
        if (it == GridListType.List) MyListLoadingBox(contentPadding = PaddingValues(0.dp))
        else MyGridLoadingBox(contentPadding = PaddingValues(0.dp), gridCellCount = gridCellCount)
    },
    emptyContent: @Composable () -> Unit = { MyPagingErrorBox() },

    appendErrorContent: @Composable ((retry: () -> Unit) -> Unit)? = { retry ->
        MyPagingErrorBox(text = "데이터를 더 불러오지 못했습니다.", onRetry = retry)
    },
    appendLoadingContent: @Composable () -> Unit = { MyBottomLoadingBox() },
    appendEndContent: @Composable () -> Unit = { MyPagingBottomEmptyBox() },

    key: ((index: Int) -> Any)? = null,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(10.dp),
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(10.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onRefresh: (refresh: () -> Unit) -> Unit = { it() },
    listState: LazyListState = rememberLazyListState(),
    gridState: LazyGridState = rememberLazyGridState(),

    placeholderContent: @Composable (Int, GridListType) -> Unit = { _, _ ->
        Box(Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp))
    },
    itemContent: @Composable (Int, T, GridListType) -> Unit
) {
    require(gridCellCount > 0) { "gridCellCount 必须大于 0" }
    val refreshState = pagingItems.loadState.refresh

    // 保存上次布局类型，旋转重建时保留调用方恢复的滚动状态。
    var oldGridListType: GridListType by rememberSaveable { mutableStateOf(gridListType) }

    // 只在切换视图类型时同步滚动位置
    LaunchedEffect(gridListType) {

        if (gridListType == oldGridListType) {
            // 如果没有切换视图类型, 则不需要同步滚动位置
            return@LaunchedEffect
        }
        oldGridListType = gridListType
        when (gridListType) {
            GridListType.List -> {
                // 两种布局的条目高度不同，仅同步索引，避免像素偏移跳过条目。
                listState.scrollToItem(gridState.firstVisibleItemIndex)
            }

            GridListType.Grid -> {
                // 网格会将该索引所在行对齐到顶部。
                gridState.scrollToItem(listState.firstVisibleItemIndex)
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = refreshState is LoadState.Loading && pagingItems.itemCount > 0,
        onRefresh = { onRefresh { pagingItems.refresh() } },
        modifier = modifier
    ) {
        when (gridListType) {
            GridListType.List -> {
                LazyColumnContent(
                    pagingItems = pagingItems,
                    vertical = verticalArrangement,
                    state = listState,
                    key = key,
                    itemContent = itemContent,
                    appendEndContent = appendEndContent,
                    appendLoadingContent = appendLoadingContent,
                    appendErrorContent = appendErrorContent,
                    contentItemsBefore = contentItemsBefore?.let { content ->
                        {
                            item { content() }
                        }
                    },
                    headerContent = headerContent?.let { content ->
                        {
                            stickyHeader { content() }
                        }
                    },
                    loadErrorContent = loadErrorContent,
                    loadingContent = loadingContent,
                    emptyContent = emptyContent,
                    contentPadding = contentPadding,
                    placeholderContent = placeholderContent,
                    modifier = Modifier.fillMaxSize()
                )
            }

            GridListType.Grid -> {
                LazyGridContent(
                    pagingItems = pagingItems,
                    vertical = verticalArrangement,
                    horizontal = horizontalArrangement,
                    state = gridState,
                    gridCellCount = gridCellCount,
                    key = key,
                    itemContent = itemContent,
                    appendEndContent = appendEndContent,
                    appendLoadingContent = appendLoadingContent,
                    appendErrorContent = appendErrorContent,
                    contentItemsBefore = contentItemsBefore?.let { content ->
                        {
                            maxWidthGridItem { content() }
                        }
                    },
                    headerContent = headerContent?.let { content ->
                        {
                            stickyHeader { content() }
                        }
                    },
                    loadErrorContent = loadErrorContent,
                    loadingContent = loadingContent,
                    emptyContent = emptyContent,
                    contentPadding = contentPadding,
                    placeholderContent = placeholderContent,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}

/** 渲染列表分页；刷新已有数据时保留条目，首屏为空时展示整体状态。 */
@Composable
private fun <T : Any> LazyColumnContent(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    key: ((index: Int) -> Any)? = null,
    contentItemsBefore: (LazyListScope.() -> Unit)? = null,
    headerContent: (LazyListScope.() -> Unit)? = null,
    itemContent: @Composable (Int, T, GridListType) -> Unit,
    placeholderContent: @Composable (Int, GridListType) -> Unit,
    appendEndContent: @Composable () -> Unit = {},
    appendLoadingContent: @Composable () -> Unit = {},
    appendErrorContent: @Composable ((retry: () -> Unit) -> Unit)? = null,
    loadErrorContent: @Composable ((refresh: () -> Unit) -> Unit)? = null,
    loadingContent: @Composable (GridListType) -> Unit = {},
    emptyContent: @Composable () -> Unit = {},
    vertical: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(10.dp),
    contentPadding: PaddingValues = PaddingValues(15.dp, 0.dp, 15.dp, 0.dp),
) {
    val refreshState = pagingItems.loadState.refresh
    val appendState = pagingItems.loadState.append

    LazyColumn(
        modifier = modifier,
        state = state,
        verticalArrangement = vertical,
        contentPadding = contentPadding,
    ) {
        contentItemsBefore?.invoke(this)
        headerContent?.invoke(this)

        when {
            pagingItems.itemCount == 0 && refreshState is LoadState.Error -> {
                loadErrorContent?.let {
                    item { it { pagingItems.refresh() } }
                }
            }

            pagingItems.itemCount == 0 && refreshState is LoadState.Loading -> {
                item {
                    loadingContent(GridListType.List)
                }
            }

            else -> {
                if (pagingItems.itemCount == 0) {
                    item {
                        emptyContent()
                    }
                    return@LazyColumn
                }

                if (refreshState is LoadState.Error) {
                    loadErrorContent?.let { content ->
                        item { content { pagingItems.refresh() } }
                    }
                }

                items(
                    pagingItems.itemCount,
                    key = key ?: pagingItems.itemKey()
                ) { index ->
                    val item = pagingItems[index]
                    if (item == null) placeholderContent(index, GridListType.List)
                    else itemContent(index, item, GridListType.List)
                }

                when (appendState) {
                    is LoadState.Error -> {
                        appendErrorContent?.let {
                            item { it { pagingItems.retry() } }
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            appendLoadingContent()
                        }
                    }

                    is LoadState.NotLoading -> {
                        //没有更多数据了, 也就是最后一页了
                        if (refreshState is LoadState.NotLoading && appendState.endOfPaginationReached) {
                            item {
                                appendEndContent()
                            }
                        }
                    }
                }
            }
        }
    }
}

/** 渲染网格分页；头部、错误、空态及分页底部均占满整行。 */
@Composable
private fun <T : Any> LazyGridContent(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    gridCellCount: Int = 2,
    key: ((index: Int) -> Any)? = null,
    contentItemsBefore: (LazyGridScope.() -> Unit)? = null,
    headerContent: (LazyGridScope.() -> Unit)? = null,
    itemContent: @Composable (Int, T, GridListType) -> Unit,
    placeholderContent: @Composable (Int, GridListType) -> Unit,
    appendEndContent: @Composable () -> Unit = {},
    appendLoadingContent: @Composable () -> Unit = {},
    appendErrorContent: @Composable ((retry: () -> Unit) -> Unit)? = null,
    loadErrorContent: @Composable ((refresh: () -> Unit) -> Unit)? = null,
    loadingContent: @Composable (GridListType) -> Unit = {},
    emptyContent: @Composable () -> Unit = {},
    vertical: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(10.dp),
    horizontal: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(10.dp),
    contentPadding: PaddingValues = PaddingValues(15.dp, 0.dp, 15.dp, 0.dp),
) {
    val refreshState = pagingItems.loadState.refresh
    val appendState = pagingItems.loadState.append

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridCellCount),
        state = state,
        contentPadding = contentPadding,
        modifier = modifier,
        verticalArrangement = vertical,
        horizontalArrangement = horizontal
    ) {
        contentItemsBefore?.invoke(this)
        headerContent?.invoke(this)

        when {
            pagingItems.itemCount == 0 && refreshState is LoadState.Error -> {
                loadErrorContent?.let {
                    maxWidthGridItem { it { pagingItems.refresh() } }
                }
            }

            pagingItems.itemCount == 0 && refreshState is LoadState.Loading -> {
                maxWidthGridItem {
                    loadingContent(GridListType.Grid)
                }
            }

            else -> {
                if (pagingItems.itemCount == 0) {
                    maxWidthGridItem { emptyContent() }
                    return@LazyVerticalGrid
                }

                if (refreshState is LoadState.Error) {
                    loadErrorContent?.let { content ->
                        maxWidthGridItem { content { pagingItems.refresh() } }
                    }
                }

                items(
                    pagingItems.itemCount,
                    key = key ?: pagingItems.itemKey()
                ) { index ->
                    val item = pagingItems[index]
                    if (item == null) placeholderContent(index, GridListType.Grid)
                    else itemContent(index, item, GridListType.Grid)
                }

                when (appendState) {
                    is LoadState.Error -> {
                        appendErrorContent?.let {
                            maxWidthGridItem { it { pagingItems.retry() } }
                        }
                    }

                    is LoadState.Loading -> {
                        maxWidthGridItem {
                            appendLoadingContent()
                        }
                    }

                    is LoadState.NotLoading -> {
                        //没有更多数据了, 也就是最后一页了
                        if (refreshState is LoadState.NotLoading && appendState.endOfPaginationReached) {
                            maxWidthGridItem {
                                appendEndContent()
                            }
                        }
                    }
                }
            }
        }

    }
}

/** 在分页网格中添加横跨所有列的条目。 */
fun LazyGridScope.maxWidthGridItem(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}
