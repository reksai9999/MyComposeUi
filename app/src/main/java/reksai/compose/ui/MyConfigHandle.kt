package reksai.compose.ui

import androidx.compose.material3.Typography
import reksai.compose.core.interfaces.MyHandleInterface
import reksai.compose.core.theme.BaseColors
import reksai.compose.core.theme.BaseShapes
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

    override fun getBaseColor(): BaseColors {
        return BaseColors()
    }

    override fun getBaseShapes(): BaseShapes {
        return BaseShapes()
    }

    override fun getBaseTypography(): Typography {
        return Typography()
    }
}