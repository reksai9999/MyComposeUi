package reksai.compose.core.component.webview

import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.ViewModel

class MySuperWebViewModel : ViewModel() {
    private val webViewMap = mutableMapOf<String, WebView>()

    fun getOrCreateWebView(key: String, context: Context): Pair<WebView, Boolean> {
        var isNew = false
        val webView = webViewMap.getOrPut(key) {
            isNew = true
            WebView(context) // 使用 Context 创建 WebView
        }
        return Pair(webView, isNew)
    }

    override fun onCleared() {
        super.onCleared()
        // 遍历销毁所有 WebView
        webViewMap.values.forEach { webView ->
            webView.stopLoading()
            webView.clearHistory()
            webView.removeAllViews()
            webView.destroy()
        }
        webViewMap.clear()
    }
}
