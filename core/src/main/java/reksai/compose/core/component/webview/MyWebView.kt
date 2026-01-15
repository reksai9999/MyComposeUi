package reksai.compose.core.component.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import reksai.compose.core.config.MyGlobalConfig

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MyWebView(
    modifier: Modifier = Modifier,
    url: String = "",
    data: String = "",
    postData: ByteArray? = null,
    webView: WebView? = null,
    isClearAllCache: Boolean = false,
    isDebug: Boolean = false,
    onCreated: (WebView) -> Unit = {},
    onPageFinished: (WebView?, String?) -> Unit = { _, _ -> },
    onShouldOverrideUrlLoading: (WebView?, WebResourceRequest?) -> Boolean = { _, _ -> false },
    onError: (WebView?, WebResourceRequest?, WebResourceError?) -> Unit = { _, _, _ -> },
) {
    AndroidView(
        factory = { context ->
            val webViewInstance = webView ?: WebView(context)
            webViewInstance.apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return onShouldOverrideUrlLoading(view, request)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onPageFinished(view, url)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        onError(view, request, error)
                    }
                }
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

                onCreated(this)

                if (isDebug) {
                    setWebContentsDebuggingEnabled(true);
                }

                if (data.isNotEmpty()) {
                    loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
                } else if (postData != null && url.isNotEmpty()) {
                    postUrl(url, postData)
                } else if (url.isNotEmpty()) {
                    loadUrl(url)
                }
            }
        },
        modifier = modifier
    )
}

class JsToAndroidImagePreview {
    @JavascriptInterface
    fun onImageClick(url: String?) {
        if (url.isNullOrEmpty()) {
            return
        }

        MyGlobalConfig.openImagePreview(url, listOf(url))
    }

    @JavascriptInterface
    fun onImageListClick(url: String?, urlListStr: String?) {
        if (url.isNullOrEmpty() || urlListStr.isNullOrEmpty()) return

        val urlList = buildList {
            val jsonArray = org.json.JSONArray(urlListStr)
            for (i in 0 until jsonArray.length()) {
                jsonArray.optString(i)?.let { add(it) }
            }
        }

        if (urlList.isEmpty()) return
        MyGlobalConfig.openImagePreview(url, urlList)
    }
}