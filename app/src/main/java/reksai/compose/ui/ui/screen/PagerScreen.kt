package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.image.MyImage
import reksai.compose.core.component.pager.DefaultTextPagerIndicator
import reksai.compose.core.component.pager.MyPager
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@Composable
fun PagerScreen(
    modifier: Modifier = Modifier,
) {
    val imageList = listOf(
        "https://picsum.photos/id/1015/600/400",
        "https://picsum.photos/id/1016/600/400",
        "https://picsum.photos/id/1018/600/400",
        "https://picsum.photos/id/1020/600/400",
        "https://picsum.photos/id/1024/600/400",
    )
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Pager",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                MyPager(
                    items = imageList,
                    pageSpacing = 0.dp,
                ) { item, _ ->

                    MyImage(
                        item,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxSize()
                            .clip(LocalShapes.current.medium)
                    )

                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                MyPager(
                    items = imageList,
                    initPageIndex = 2,
                    indicator = { actualCurrentPage, totalPage, pagerState ->
                        DefaultTextPagerIndicator(
                            page = actualCurrentPage,
                            totalPage = totalPage,
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                ) { item, pagerState ->
                    MyImage(
                        item,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }

        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun PagerScreenPreview() {
    PagerScreen(
        modifier = Modifier.fillMaxSize()
    )
}