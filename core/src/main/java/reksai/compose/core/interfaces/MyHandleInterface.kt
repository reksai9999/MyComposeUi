package reksai.compose.core.interfaces

interface MyHandleInterface {
    /**
     * 顶部栏返回按钮的返回事件
     */
    fun topBarBack()

    /**
     * 打开图片预览
     * @param url 当前图片地址
     * @param urlList 图片地址列表
     */
    fun openImagePreview(url: String, urlList: List<String> = listOf(url))

    /**
     * 获取FileProvider
     */
    fun getFileProvider(): String

//    /**
//     * 发送SnackBar消息
//     * @param message 消息内容
//     */
//    fun sendSnackBarMessage(message: String)
}