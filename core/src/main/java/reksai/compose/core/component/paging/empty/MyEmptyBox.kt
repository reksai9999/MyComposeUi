package reksai.compose.core.component.paging.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.R
import reksai.compose.core.theme.LocalTypography

/** 使用 drawable 矢量资源展示分页空态图标和说明。 */
@Composable
fun MyEmptyBox(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = ImageVector.vectorResource(R.drawable.icon_nothing),
    text: String = "검색 결과가 없습니다.",
    textStyle: TextStyle = LocalTypography.current.bodyMedium,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .heightIn(min = 200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = text,
                style = textStyle,
            )
        }
    }
}

/** 预览默认分页状态组件。 */
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyEmptyBoxPreview() {
    MyEmptyBox()
}
