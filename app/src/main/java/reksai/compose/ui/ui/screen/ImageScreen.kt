package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.component.image.MyImage
import reksai.compose.core.component.image.MyImagePreview
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import java.lang.Math.random

@Composable
fun ImageScreen(
    modifier: Modifier = Modifier,
) {
    val random = random() * 10
    val imgList = listOf(
        "https://picsum.photos/seed/${random}_1/1080",
        "https://picsum.photos/seed/${random}_2/1080",
        "https://picsum.photos/seed/${random}_3/1080",
    )
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Image",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
        ) {

            Text(
                text = "网络图片载入",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier
            )
            var imgId by remember { mutableIntStateOf(1) }
            MyImage(
                image = "https://picsum.photos/id/${imgId}/220",
                modifier = Modifier.size(120.dp, 150.dp)
            )
            MyOutlineButton(
                text = "重新载入图片"
            ) {
                imgId++
            }

            Text(
                text = "图片预览",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier
            )

            MyImagePreview(
                url = imgList.first(),
                urlList = imgList,
                modifier = Modifier.size(120.dp, 150.dp)
            )

            MyImagePreview(
                url = imgList.first(),
                urlList = imgList,
                showClose = false,
                modifier = Modifier.size(120.dp, 150.dp)
            )


        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun ImageScreenPreview() {
    ImageScreen(
        modifier = Modifier.fillMaxSize()
    )
}