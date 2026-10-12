package reksai.compose.core.component.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import reksai.compose.core.R
import reksai.compose.core.component.paging.empty.MyEmptyBox
import reksai.compose.core.theme.LocalTypography

/** 展示分页空态或加载错误；传入 onRetry 时提供明确的重试入口。 */
@Composable
fun MyPagingErrorBox(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = ImageVector.vectorResource(R.drawable.icon_nothing),
    text: String = "검색 결과가 없습니다.",
    textStyle: TextStyle = LocalTypography.current.bodySmall.copy(textAlign = TextAlign.Center),
    onRetry: (() -> Unit)? = null,
    retryText: String = "다시 시도",
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MyEmptyBox(imageVector = imageVector, text = text, textStyle = textStyle)
        onRetry?.let { retry ->
            TextButton(onClick = retry) { Text(retryText, style = textStyle) }
        }
    }
}

/** 展示已经到达最后一页的提示，可通过插槽或参数替换文案。 */
@Composable
fun MyPagingBottomEmptyBox(
    modifier: Modifier = Modifier,
    text: String = "더 이상 데이터가 없습니다.",
    textStyle: TextStyle = LocalTypography.current.bodyMedium,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = textStyle)
    }
}
