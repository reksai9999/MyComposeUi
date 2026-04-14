package reksai.compose.core.component.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MySuperWebView(
    modifier: Modifier = Modifier,
    key: String = "default",
    url: String = "",
    data: String = "",
    postData: ByteArray? = null,
    isClearAllCache: Boolean = false,
    isDebug: Boolean = false,
    onCreated: (WebView) -> Unit = {},
    onPageFinished: (WebView?, String?) -> Unit = { _, _ -> },
    onShouldOverrideUrlLoading: (WebView?, WebResourceRequest?) -> Boolean = { _, _ -> false },
    onError: (WebView?, WebResourceRequest?, WebResourceError?) -> Unit = { _, _, _ -> },
) {
    val viewModel: MySuperWebViewModel = viewModel()

    val currentOnCreated by rememberUpdatedState(onCreated)
    val currentOnPageFinished by rememberUpdatedState(onPageFinished)
    val currentOnShouldOverrideUrlLoading by rememberUpdatedState(onShouldOverrideUrlLoading)
    val currentOnError by rememberUpdatedState(onError)

    AndroidView(
        factory = { context ->
            val (webViewInstance, isNew) = viewModel.getOrCreateWebView(key, context)

            // 如果已经有父视图，先移除
            (webViewInstance.parent as? ViewGroup)?.removeView(webViewInstance)

            val webViewClientImpl = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return currentOnShouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    currentOnPageFinished(view, url)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    currentOnError(view, request, error)
                }
            }

            if (isNew) {
                webViewInstance.apply {
                    webViewClient = webViewClientImpl
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        displayZoomControls = false
                        builtInZoomControls = false
                        defaultTextEncodingName = "utf-8"
                        cacheMode = WebSettings.LOAD_DEFAULT
                        allowFileAccess = true
                    }
                    setLayerType(WebView.LAYER_TYPE_HARDWARE, null)

                    if (isClearAllCache) {
                        clearCache(true)
                        clearHistory()
                        clearFormData()
                        val cookieManager = android.webkit.CookieManager.getInstance()
                        cookieManager.removeAllCookies(null)
                        cookieManager.flush()
                        android.webkit.WebStorage.getInstance().deleteAllData()
                    }

                    currentOnCreated(this)

                    if (isDebug) {
                        setWebContentsDebuggingEnabled(true)
                    }

                    if (data.isNotEmpty()) {
                        loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
                    } else if (postData != null && url.isNotEmpty()) {
                        postUrl(url, postData)
                    } else if (url.isNotEmpty()) {
                        loadUrl(url)
                    }
                }
            } else {
                webViewInstance.webViewClient = webViewClientImpl
            }
            webViewInstance
        },
        modifier = modifier
    )
}
