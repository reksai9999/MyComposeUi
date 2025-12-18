package reksai.compose.core.config

import reksai.compose.core.interfaces.MyHandleInterface

object MyConfig {
    var appHandle: MyHandleInterface? = null
    fun init(appHandle: MyHandleInterface) {
        this.appHandle = appHandle
    }

    fun topBarBack() {
        appHandle?.topBarBack()
    }

    fun openImagePreview(url: String, urlList: List<String> = listOf(url)) {
        appHandle?.openImagePreview(url, urlList)
    }

    fun getFileProvider(): String {
        return appHandle?.getFileProvider() ?: ""
    }
}