package reksai.compose.core.component.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors

/**
 * 默认的页面指示器 球形
 */
@Composable
fun DefaultBallPagerIndicator(
    page: Int,
    totalPage: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = LocalColors.current.red,
    inactiveColor: Color = LocalColors.current.gray400,
    indicatorSize: Dp = 7.dp,
    spacing: Dp = 4.dp
) {
    if (totalPage <= 1) return

    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPage) { index ->
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(CircleShape)
                    .background(
                        if (index == page) activeColor else inactiveColor
                    )
            )
        }
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun DefaultBallPagerIndicatorPreview() {
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

