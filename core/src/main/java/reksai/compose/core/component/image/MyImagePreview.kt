package reksai.compose.core.component.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.icon.MyIconClose
import reksai.compose.core.config.MyConfig
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@Composable
fun MyImagePreview(
    url: String,
    modifier: Modifier = Modifier,
    urlList: List<String> = listOf(url),
    shape: Shape = LocalShapes.current.medium,
    showClose: Boolean = true,
    onClose: () -> Unit = { },
    onClick: () -> Unit = {
        MyConfig.openImagePreview(url, urlList)
    }
) {

    Box(
        modifier = Modifier
            .clip(shape)
            .then(modifier)
    ) {
        MyImage(
            image = url,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickableNormalNoEffect(onClick)
        )

        if (showClose) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.TopEnd)
                    .clip(LocalShapes.current.circle)
                    .background(LocalColors.current.blackOpacity30)
                    .clickableNormalNoEffect(onClose)
            ) {
                MyIconClose(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(18.dp)
                )
            }
        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyImagePreviewPreview() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        MyImagePreview(
            url = "https://example.com/image.jpg",
            modifier = Modifier
                .width(120.dp)
                .height(150.dp)
                .background(LocalColors.current.gray300)
        )
    }

}