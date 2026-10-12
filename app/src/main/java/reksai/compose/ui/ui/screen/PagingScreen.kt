package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.paging.MyPaging
import reksai.compose.core.component.paging.MyPagingBottomEmptyBox
import reksai.compose.core.component.paging.MyPagingErrorBox
import reksai.compose.core.component.paging.enums.GridListType
import reksai.compose.core.component.paging.loading.MyBottomLoadingBox
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography
import reksai.compose.core.theme.MyTheme

/** 从 ViewModel 收集真实的本地分页流，屏幕旋转时复用分页数据。 */
@Composable
fun PagingScreen(modifier: Modifier = Modifier) {
    val viewModel: PagingDemoViewModel = viewModel()
    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    val scenario by viewModel.selectedScenario.collectAsState()
    PagingScreenContent(
        pagingItems = pagingItems,
        modifier = modifier,
        scenario = scenario,
        onScenarioChange = viewModel::selectScenario,
    )
}

/** 演示分页插槽、布局切换和失败重试，布局选择由示例页提升管理。 */
@Composable
private fun PagingScreenContent(
    pagingItems: LazyPagingItems<PagingDemoItem>,
    modifier: Modifier = Modifier,
    scenario: PagingDemoScenario = PagingDemoScenario.Normal,
    onScenarioChange: (PagingDemoScenario) -> Unit = {},
) {
    var gridListType by rememberSaveable { mutableStateOf(GridListType.List) }

    Column(modifier = modifier
        .fillMaxSize()
        .background(LocalColors.current.background)) {
        MyTopBar(title = "Paging 分页")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilterChip(
                selected = gridListType == GridListType.List,
                onClick = { gridListType = GridListType.List },
                label = { Text("列表") },
            )
            FilterChip(
                selected = gridListType == GridListType.Grid,
                onClick = { gridListType = GridListType.Grid },
                label = { Text("网格") },
            )
            TextButton(onClick = { pagingItems.refresh() }) { Text("刷新") }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PagingDemoScenario.entries.forEach { entry ->
                FilterChip(
                    selected = scenario == entry,
                    onClick = {
                        onScenarioChange(entry)
                    },
                    label = { Text(entry.title) },
                )
            }
        }

        MyPaging(
            pagingItems = pagingItems,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .navigationBarsPadding(),
            gridListType = gridListType,
            gridCellCount = 2,
            contentPadding = PaddingValues(16.dp),
            key = pagingItems.itemKey { it.id },
            contentItemsBefore = {
                Text(
                    text = "每页 20 条，共 60 条。下拉刷新，滑到底部加载下一页。失败场景只模拟一次错误，点击重试即可恢复。",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = LocalTypography.current.bodySmall,
                    color = LocalColors.current.gray800,
                )
            },
            headerContent = {
                val status = if (pagingItems.loadState.refresh is LoadState.Loading) {
                    "加载中"
                } else {
                    "已加载 ${pagingItems.itemCount} 条"
                }
                Text(
                    text = "${scenario.title} · $status",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LocalColors.current.background)
                        .padding(vertical = 10.dp),
                    style = LocalTypography.current.titleSmall,
                )
            },
            loadErrorContent = { refresh ->
                MyPagingErrorBox(text = "首屏加载失败，请重试", onRetry = refresh, retryText = "重试")
            },
            emptyContent = { MyPagingErrorBox(text = "暂无数据，切换到正常数据继续体验") },
            appendErrorContent = { retry ->
                MyPagingErrorBox(text = "下一页加载失败，已加载内容仍可浏览", onRetry = retry, retryText = "重试下一页")
            },
            appendLoadingContent = { MyBottomLoadingBox(text = "正在加载下一页…") },
            appendEndContent = { MyPagingBottomEmptyBox(text = "已加载全部 60 条数据") },
        ) { _, item, layout ->
            PagingItemCard(item = item, gridListType = layout)
        }
    }
}

/** 使用本地颜色块展示列表和网格条目，不依赖网络图片。 */
@Composable
private fun PagingItemCard(item: PagingDemoItem, gridListType: GridListType) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = LocalShapes.current.medium,
        colors = CardDefaults.cardColors(containerColor = LocalColors.current.white200),
    ) {
        if (gridListType == GridListType.List) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PagingItemNumber(item.id, Modifier.size(64.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(item.title, style = LocalTypography.current.titleSmall)
                    Text(item.description, style = LocalTypography.current.bodySmall)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PagingItemNumber(item.id, Modifier
                    .fillMaxWidth()
                    .height(90.dp))
                Text(item.title, style = LocalTypography.current.titleSmall)
                Text(item.description, style = LocalTypography.current.bodySmall)
            }
        }
    }
}

/** 绘制编号缩略图，方便观察切换布局时的滚动位置。 */
@Composable
private fun PagingItemNumber(id: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(LocalShapes.current.small)
            .background(LocalColors.current.blue100.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center,
    ) {
        Text("$id", style = LocalTypography.current.titleLarge, color = LocalColors.current.blue100)
    }
}

internal enum class PagingDemoScenario(val title: String) {
    Normal("正常数据"), Empty("空数据"), RefreshError("首屏失败"), AppendError("下一页失败")
}

internal data class PagingDemoItem(val id: Int) {
    val title = "分页条目 $id"
    val description = "第 ${(id - 1) / 20 + 1} 页 · 本地模拟数据"
}

/** 缓存分页流，切换场景时创建新的 Pager，并重置该场景的一次性失败标记。 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class PagingDemoViewModel : ViewModel() {
    private val scenario = MutableStateFlow(PagingDemoScenario.Normal)
    val selectedScenario = scenario.asStateFlow()

    val pagingFlow = scenario.asStateFlow().flatMapLatest { selected ->
        val failurePlan = PagingDemoFailurePlan(selected)
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 3, enablePlaceholders = false),
            pagingSourceFactory = { PagingDemoSource(selected, failurePlan) },
        ).flow
    }.cachedIn(viewModelScope)

    /** 切换分页数据源；布局切换不重建 Pager。 */
    fun selectScenario(selected: PagingDemoScenario) {
        scenario.value = selected
    }
}

/** 同一场景内共享失败标记，refresh 创建新数据源后也能恢复正常加载。 */
private class PagingDemoFailurePlan(scenario: PagingDemoScenario) {
    var refreshPending = scenario == PagingDemoScenario.RefreshError
    var appendPending = scenario == PagingDemoScenario.AppendError
}

/** 以数据偏移量作为分页键，模拟首屏及追加请求的延迟和可恢复错误。 */
private class PagingDemoSource(
    private val scenario: PagingDemoScenario,
    private val failurePlan: PagingDemoFailurePlan,
) : PagingSource<Int, PagingDemoItem>() {
    /** 根据可见位置重新选择偏移量，刷新时保留当前位置附近的数据。 */
    override fun getRefreshKey(state: PagingState<Int, PagingDemoItem>): Int? {
        val anchor = state.anchorPosition ?: return null
        val item = state.closestItemToPosition(anchor) ?: return null
        return ((item.id - 1) - state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    /** 每次请求最多返回请求范围内的数据，60 条后结束分页。 */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingDemoItem> {
        delay(800)
        if (params is LoadParams.Refresh && failurePlan.refreshPending) {
            failurePlan.refreshPending = false
            return LoadResult.Error(IllegalStateException("模拟首屏加载失败"))
        }
        if (params is LoadParams.Append && failurePlan.appendPending) {
            failurePlan.appendPending = false
            return LoadResult.Error(IllegalStateException("模拟下一页加载失败"))
        }
        if (scenario == PagingDemoScenario.Empty) {
            return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
        }
        val offset = params.key ?: 0
        val start = if (params is LoadParams.Prepend) (offset - params.loadSize).coerceAtLeast(0) else offset
        val end = if (params is LoadParams.Prepend) offset else (offset + params.loadSize).coerceAtMost(60)
        return LoadResult.Page(
            data = (start until end).map { PagingDemoItem(it + 1) },
            prevKey = start.takeIf { it > 0 },
            nextKey = end.takeIf { it < 60 },
        )
    }
}

/** 使用静态分页快照预览界面，避免预览工具发起异步模拟请求。 */
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun PagingScreenPreview() {
    val flow = remember {
        flowOf(
            PagingData.from(
                data = (1..20).map { PagingDemoItem(it) },
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(false),
                ),
            )
        )
    }
    MyTheme { PagingScreenContent(pagingItems = flow.collectAsLazyPagingItems()) }
}

