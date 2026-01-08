package reksai.compose.core.component.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyBadgedBox(
    modifier: Modifier = Modifier,
    badgeModifier: Modifier = Modifier,
    count: Int = 0,
    style: TextStyle = LocalTypography.current.labelSmall,
    onlyPoint: Boolean = false,
    backgroundColor: Color = Color.Red,
    textColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    BadgedBox(
        badge = {
            if (count > 0) {
                if (!onlyPoint) {
                    Badge(
                        containerColor = backgroundColor,
                        contentColor = textColor,
                        modifier = Modifier.then(badgeModifier)
                    ) {
                        Text(
                            text = if (count > 99) "99+" else count.toString(),
                            style = style,
                            modifier = Modifier
                        )
                    }
                } else {
                    Badge(
                        containerColor = backgroundColor,
                    )
                }

            }
        },
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun MyBadgedBox(
    show: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Red,
    content: @Composable () -> Unit
) {
    MyBadgedBox(
        modifier = modifier,
        count = if (show) 1 else 0,
        onlyPoint = true,
        backgroundColor = backgroundColor,
        content = content
    )
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyBadgedBoxPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MyBadgedBox(
            count = 99
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
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