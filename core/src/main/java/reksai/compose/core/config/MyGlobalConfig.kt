package reksai.compose.core.config

import androidx.compose.material3.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import reksai.compose.core.interfaces.MyHandleInterface
import reksai.compose.core.theme.BaseColors
import reksai.compose.core.theme.BaseShapes

object MyGlobalConfig {
    private var _appHandle: MyHandleInterface? = null

    private val _snackBarMessageFlow = MutableSharedFlow<String>()
    val snackBarMessageFlow = _snackBarMessageFlow.asSharedFlow()
    fun init(appHandle: MyHandleInterface) {
        this._appHandle = appHandle
    }

    /**
     * 顶部栏返回按钮的返回事件
     */
    fun topBarBack() {
        _appHandle?.topBarBack()
    }

    /**
     * 打开图片预览
     * @param url 当前图片地址
     * @param urlList 图片地址列表
     */
    fun openImagePreview(url: String, urlList: List<String> = listOf(url)) {
        _appHandle?.openImagePreview(url, urlList)
    }

    /**
     * 获取FileProvider
     */
    fun getFileProvider(): String {
        return _appHandle?.getFileProvider() ?: ""
    }

    fun getBaseColor(): BaseColors {
        return _appHandle?.getBaseColor() ?: BaseColors()
    }

    fun getBaseShapes(): BaseShapes {
        return _appHandle?.getBaseShapes() ?: BaseShapes()
    }

    fun getBaseTypography(): Typography {
        return _appHandle?.getBaseTypography() ?: Typography()
    }

    /**
     * 发送SnackBar消息
     * @param message 消息内容
     */
    fun sendSnackBarMessage(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _snackBarMessageFlow.emit(message)
        }
    }

}