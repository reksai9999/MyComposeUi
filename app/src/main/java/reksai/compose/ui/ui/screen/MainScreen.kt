package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import reksai.compose.ui.ui.navigation.MyRoute
import reksai.compose.ui.ui.navigation.RouteAlert
import reksai.compose.ui.ui.navigation.RouteCheckBox
import reksai.compose.ui.ui.navigation.RouteColorText
import reksai.compose.ui.ui.navigation.RouteDialog
import reksai.compose.ui.ui.navigation.RouteImage
import reksai.compose.ui.ui.navigation.RouteInputText
import reksai.compose.ui.ui.navigation.RouteMenu
import reksai.compose.ui.ui.navigation.RoutePager
import reksai.compose.ui.ui.navigation.RouteSelector
import reksai.compose.ui.ui.navigation.RouteTabs

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {
        MyTopBar(
            title = "Main Screen",
            showBack = false
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .verticalScroll(rememberScrollState())
        ) {

            TitleDesc("Input") {
                MyRoute.add(RouteInputText)
            }
            TitleDesc("CheckBox") {
                MyRoute.add(RouteCheckBox)
            }
            TitleDesc("Dialog") {
                MyRoute.add(RouteDialog)
            }
            TitleDesc("Alert") {
                MyRoute.add(RouteAlert)
            }

            TitleDesc("Image") {
                MyRoute.add(RouteImage)
            }

            TitleDesc("Selector") {
                MyRoute.add(RouteSelector)
            }

            TitleDesc("Pager") {
                MyRoute.add(RoutePager)
            }

            TitleDesc("Tabs") {
                MyRoute.add(RouteTabs)
            }

            TitleDesc("Color Text") {
                MyRoute.add(RouteColorText)
            }

            TitleDesc("Menu") {
                MyRoute.add(RouteMenu)
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
        modifier = Modifier.clickableNormalNoEffect(onClick)
    ) {
        Text(
            text = title,
            style = LocalTypography.current.bodyLarge,
            color = LocalColors.current.black200,
            modifier = modifier.padding(horizontal = 15.dp, vertical = 10.dp)
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