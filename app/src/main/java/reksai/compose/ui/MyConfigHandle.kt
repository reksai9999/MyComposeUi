package reksai.compose.ui

import reksai.compose.core.interfaces.MyHandleInterface
import reksai.compose.ui.ui.navigation.MyRoute
import reksai.compose.ui.ui.navigation.RouteImagePreview

class MyConfigHandle : MyHandleInterface {
    override fun topBarBack() {
        MyRoute.back()
    }

    override fun openImagePreview(url: String, urlList: List<String>) {
        MyRoute.add(RouteImagePreview(url, urlList))
    }

    override fun getFileProvider(): String {
        return "reksai.compose.ui.provider"
    }
}