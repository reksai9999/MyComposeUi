package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyBadgedBox
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes

@Composable
fun BadgeScreenVM() {
    BadgeScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun BadgeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Badge",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MyBadgedBox(
                    count = 88
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(LocalColors.current.gray600)
                    )
                }

                MyBadgedBox(
                    count = 88
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(LocalShapes.current.circle)
                            .background(LocalColors.current.gray600)
                    )
                }

                MyBadgedBox(
                    show = true
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(LocalShapes.current.circle)
                            .background(LocalColors.current.gray600)
                    )
                }
            }
        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun BadgeScreenPreview() {
    BadgeScreen(
        modifier = Modifier.fillMaxSize()
    )
}