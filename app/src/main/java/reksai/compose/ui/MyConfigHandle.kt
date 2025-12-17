package reksai.compose.ui

import reksai.compose.core.interfaces.MyHandleInterface
import reksai.compose.ui.ui.navigation.MyRoute

class MyConfigHandle : MyHandleInterface {
    override fun topBarBack() {
        MyRoute.back()
    }

    override fun openImagePreview(url: String, urlList: List<String>) {
        TODO("Not yet implemented")
    }
}