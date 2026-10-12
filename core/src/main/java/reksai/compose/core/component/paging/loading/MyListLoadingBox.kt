package reksai.compose.core.component.paging.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.paging.loading.item.MyListLoadingItem

/** 展示列表首屏加载的骨架占位，可配置图片和文字尺寸。 */
@Composable
fun MyListLoadingBox(
    modifier: Modifier = Modifier,
    repeatCount: Int = 8,
    contentPadding: PaddingValues = PaddingValues(15.dp, 0.dp, 15.dp, 0.dp),
    verticalArrangementDp: Dp = 10.dp,
    imageWidth: Dp = 110.dp,
    textCount: Int = 7,
    textHeight: Dp = 8.dp,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(verticalArrangementDp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentPadding)
            .then(modifier),
    ) {
        repeat(repeatCount) {
            MyListLoadingItem(
                modifier = Modifier.fillMaxWidth(),
                textHeight = textHeight,
                repeatCount = textCount,
                imageWidth = imageWidth
            )
        }

    }
}

/** 预览默认分页状态组件。 */
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyListLoadingBoxPreview() {
    MyListLoadingBox()
}
