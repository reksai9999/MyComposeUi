package reksai.compose.core.component.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.zj.shimmer.shimmer
import reksai.compose.core.theme.LocalColors

@Composable
fun MyImage(
    image: Any?,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val model = remember(image) {
        ImageRequest.Builder(context)
            .data(image)
            .crossfade(true)
            .build()
    }

    AsyncImage(
        model = model,
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter,
        onState = {
            isLoading = when (it) {
                is AsyncImagePainter.State.Loading -> {
                    true
                }

                is AsyncImagePainter.State.Error -> {
                    false
                }

                is AsyncImagePainter.State.Success -> {
                    false
                }

                is AsyncImagePainter.State.Empty -> {
                    false
                }
            }
        },
        modifier = modifier.then(
            if (isLoading) {
                Modifier
                    .shimmer(true)
                    .background(color = LocalColors.current.gray200)
            } else {
                Modifier
            }
        )
    )
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyImageContentPreview() {
    Column(
        modifier = Modifier
            .background(LocalColors.current.background)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .shimmer(true)
                .background(color = LocalColors.current.gray200)
        ) {
            MyImage(
                image = "https://picsum.photos/200/300",
                modifier = Modifier
                    .fillMaxSize()
            )
        }

    }


}