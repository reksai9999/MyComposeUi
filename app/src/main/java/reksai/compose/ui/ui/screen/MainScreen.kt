package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import reksai.compose.ui.ui.navigation.MyRoute
import reksai.compose.ui.ui.navigation.RouteDialog

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {

        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
        ) {

            TitleDesc("Input")
            TitleDesc("Dialog") {
                MyRoute.add(RouteDialog)
            }

        }
    }

}

@Composable
fun TitleDesc(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.clickableNormalNoEffect(onClick)
    ) {
        Text(
            text = title,
            style = LocalTypography.current.bodyLarge,
            color = LocalColors.current.black200,
            modifier = modifier.padding(horizontal = 15.dp)
        )
        HorizontalDivider()
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(
        modifier = Modifier.fillMaxSize()
    )
}