package reksai.compose.core.component.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.MutableContextWrapper
import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.ViewModel

class MySuperWebViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private var webView: WebView? = null

    var isNew: Boolean = false
        private set

    fun getOrCreateWebView(context: Context): WebView {
        if (webView == null) {
            isNew = true
            // 使用 MutableContextWrapper 包装 Context，避免 ViewModel 强引用导致 Activity 内存泄漏
            webView = WebView(MutableContextWrapper(context))
        } else {
            isNew = false
            // 每次获取时，更新 baseContext 为最新的 Context，防止因为屏幕旋转等导致引用的还是旧的 Activity Context
            (webView?.context as? MutableContextWrapper)?.baseContext = context
        }
        return webView!!
    }

    override fun onCleared() {
        super.onCleared()
        webView?.let {
            it.stopLoading()
            it.clearHistory()
            it.removeAllViews()
            it.destroy()
        }
        webView = null

        Log.d("webView", "webView 已经释放 webView = null")
    }
}
