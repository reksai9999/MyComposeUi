package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.github.panpf.zoomimage.GlideZoomAsyncImage
import com.github.panpf.zoomimage.compose.glide.ExperimentalGlideComposeApi
import com.github.panpf.zoomimage.compose.glide.placeholder
import reksai.compose.core.component.loading.LineSpinFadeLoaderIndicator
import reksai.compose.core.theme.LocalColors
import reksai.compose.ui.ui.navigation.MyRoute

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImagePreviewScreen(
    url: String,
    urlList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = urlList.indexOf(url),
        pageCount = {
            urlList.size
        }
    )
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .background(LocalColors.current.black200)
    ) { page ->
        GlideZoomAsyncImage(
            model = urlList[page],
            contentDescription = "view image",
            contentScale = ContentScale.FillWidth,
            onTap = {
                MyRoute.back()
            },
            loading = placeholder {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LineSpinFadeLoaderIndicator(
                        color = LocalColors.current.white,
                        rectCount = 10,
                        penThickness = 10f,
                        radius = 40f,
                        elementHeight = 15f,
                    )
                }
            },
            failure = placeholder {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = "Loading failed, please try again.",
                        color = LocalColors.current.white
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun ImagePreviewScreenPreview() {
    ImagePreviewScreen(
        url = "https://example.com/image1.jpg",
        urlList = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
        modifier = Modifier.fillMaxSize()
    )
}