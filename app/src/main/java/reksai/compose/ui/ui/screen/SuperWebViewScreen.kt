package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.component.webview.MySuperWebView
import reksai.compose.core.theme.LocalColors
import reksai.compose.ui.ui.navigation.MyRoute
import reksai.compose.ui.ui.navigation.RouteAlert

@Composable
fun SuperWebViewScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Super WebView",
        )

        var url by remember { mutableStateOf("https://www.bing.com") }

        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            MyFillButton(
                text = "Open Another Page (Alert)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                MyRoute.add(RouteAlert)
            }

            Text(
                text = "向下滚动网页视图，然后打开另一个页面并返回。应保留状态。",
                color = LocalColors.current.black200,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        MySuperWebView(
            url = url,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
        )
    }
}

