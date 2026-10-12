package reksai.compose.core.component.paging.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zj.shimmer.shimmer
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import kotlin.random.Random

/** 展示网格首屏加载的骨架占位。 */
@Composable
fun MyGridLoadingBox(
    modifier: Modifier = Modifier,
    repeatCount: Int = 8,
    contentPadding: PaddingValues = PaddingValues(15.dp, 0.dp, 15.dp, 0.dp),
    verticalArrangementDp: Dp = 10.dp,
    horizontalArrangementDp: Dp = 10.dp,
    gridCellCount: Int = 2,
) {

    require(gridCellCount > 0) { "gridCellCount 必须大于 0" }
    Column(
        verticalArrangement = Arrangement.spacedBy(verticalArrangementDp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentPadding)
            .then(modifier),
    ) {
        repeat(repeatCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(horizontalArrangementDp)
            ) {
                repeat(gridCellCount) {
                    MyGridLoadingItem(
                        modifier = Modifier.weight(1f),
                        repeatCount = 4,
                    )
                }
            }
        }

    }

}

/** 绘制单个网格骨架，记住文字宽度以避免重组闪动。 */
@Composable
fun MyGridLoadingItem(
    modifier: Modifier = Modifier,
    textRandomWidth: () -> Float = { Random.nextDouble(0.5, 1.0).toFloat() },
    textHeight: Dp = 10.dp,
    repeatCount: Int = 5,
) {
    val random = remember(repeatCount) { List(repeatCount) { textRandomWidth() } }
    Column(
        modifier = modifier
            .clip(LocalShapes.current.medium)
            .background(LocalColors.current.gray100)
            .shimmer(true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .background(LocalColors.current.gray200)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 8.dp, bottom = 15.dp)
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
private fun MyGridLoadingBoxPreview() {
    MyGridLoadingBox()
}
