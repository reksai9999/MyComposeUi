package reksai.compose.core.interfaces

interface MyHandleInterface {
    fun topBarBack()

    fun openImagePreview(url: String, urlList: List<String> = listOf(url))
}