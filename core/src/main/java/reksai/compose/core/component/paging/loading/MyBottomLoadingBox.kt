package reksai.compose.core.component.paging.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography

/** 展示追加分页数据时的底部加载提示。 */
@Composable
fun MyBottomLoadingBox(
    modifier: Modifier = Modifier,
    text: String = "로딩 중입니다...",
    textStyle: TextStyle = LocalTypography.current.titleSmall.copy(fontWeight = FontWeight.Bold),
    loadingColor: Color = LocalColors.current.black200,
    contentPadding: PaddingValues = PaddingValues(20.dp, 5.dp, 20.dp, 25.dp)
) {
    Box(modifier = modifier) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            CircularProgressIndicator(
                color = loadingColor,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp,
            )
            Text(
                text = text,
                style = textStyle,
                modifier = Modifier.padding(start = 25.dp)
            )
        }
    }
}

/** 预览默认分页状态组件。 */
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyBottomLoadingBoxPreview() {
    MyBottomLoadingBox()
}
