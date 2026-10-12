package reksai.compose.core.component.paging.loading.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zj.shimmer.shimmer
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import kotlin.random.Random

/** 绘制单个列表骨架，记住文字宽度以避免重组闪动。 */
@Composable
fun MyListLoadingItem(
    modifier: Modifier = Modifier,
    textRandomWidth: () -> Float = { Random.nextDouble(0.4, 1.0).toFloat() },
    textHeight: Dp = 10.dp,
    repeatCount: Int = 5,
    imageWidth: Dp = 120.dp,
    backgroundColor: Color = LocalColors.current.gray100,
    showImage: Boolean = true,
    verticalSpaced: Dp = 4.dp,
) {
    val random = remember(repeatCount) { List(repeatCount) { textRandomWidth() } }
    Row(
        modifier = modifier
            .clip(LocalShapes.current.medium)
            .background(backgroundColor)
            .shimmer(true)
    ) {
        if (showImage) {
            Box(
                modifier = Modifier
                    .width(imageWidth)
                    .aspectRatio(1f)
                    .background(LocalColors.current.gray200)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(verticalSpaced),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 8.dp)
        ) {
            repeat(repeatCount) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(random[index])
                        .height(textHeight)
                        .background(LocalColors.current.gray200)
                )
            }
        }
    }
}

/** 预览默认分页状态组件。 */
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyListLoadingItemPreview() {

    MyListLoadingItem(
        modifier = Modifier.padding(10.dp)
    )

}
