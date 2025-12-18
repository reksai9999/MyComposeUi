package reksai.compose.core.component.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.BaseColors
import reksai.compose.core.theme.BaseShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun DefaultTextPagerIndicator(
    page: Int,
    totalPage: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTypography.current.bodySmall,
    textColor: Color = Color.White,
    backgroundColor: Color = BaseColors.Color.blackOpacity40,
) {
    if (totalPage <= 1) return

    Box(
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .background(backgroundColor, shape = BaseShapes.current.circle)
                .padding(horizontal = 15.dp, vertical = 4.dp),
        ) {
            Text(text = (page + 1).toString(), color = textColor, style = style, modifier = Modifier.widthIn(min = 8.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "/", color = textColor, style = style)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = totalPage.toString(), color = textColor, style = style, modifier = Modifier.widthIn(min = 8.dp))
        }

    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun DefaultTextPagerIndicatorPreview() {
    MyPager(
        items = listOf(1, 2, 3, 4, 5),
        initPageIndex = 2,
        indicator = { actualCurrentPage, totalPage, pagerState ->
            DefaultTextPagerIndicator(
                page = actualCurrentPage,
                totalPage = totalPage,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    ) { item, pagerState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}