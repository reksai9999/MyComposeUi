package reksai.compose.core.utils

/**
 * Compose 工具函数
 */
import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable


/**
 * 得到当前 Activity
 */
@Composable
fun getLocalActivity(): ComponentActivity {
    val context = LocalContext.current
    return context as ComponentActivity
}

@Composable
fun getLocalContext(): Context {
    return LocalContext.current
}

/**
 * 得到当前的 Activity
 */
@Composable
fun getActivity(): ComponentActivity {
    return getLocalActivity()
}

/**
 * 判断是否在预览模式下
 */
@Composable
fun isPreview(): Boolean {
    return LocalInspectionMode.current
}

/**
 * 得到 FocusManager
 */
@Composable
fun getFocusManager(): FocusManager {
    return LocalFocusManager.current
}

/**
 * 得到 SoftwareKeyboardController
 */
@Composable
fun getKeyboardController(): SoftwareKeyboardController? {
    return LocalSoftwareKeyboardController.current
}


/**
 * 获取屏幕高度 px 单位 (不包括状态栏和导航栏)
 */
@Composable
fun screenHeightPX(): Int {
    return LocalWindowInfo.current.containerSize.height
}

/**
 * 获取屏幕高度 dp 单位 (不包括状态栏和导航栏)
 */
@Composable
fun screenHeightDP(): Dp {
    return with(LocalDensity.current) { LocalWindowInfo.current.containerSize.height.toDp() }
}

/**
 * 获取屏幕宽度 px 单位 (不包括状态栏和导航栏)
 */
@Composable
fun screenWidthPX(): Int {
    return LocalWindowInfo.current.containerSize.width
}

/**
 * 获取屏幕宽度 dp 单位 (不包括状态栏和导航栏)
 */
@Composable
fun screenWidthDP(): Dp {
    return with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() }
}

/**
 *  px 转 dp
 */
@Composable
fun pxToDp(px: Int): Dp {
    return with(LocalDensity.current) {
        px.toDp()
    }
}

/**
 *  dp 转 px
 */
@Composable
fun dpToPx(dp: Dp): Int {
    return with(LocalDensity.current) {
        dp.roundToPx()
    }
}


/**
 * 深拷贝对象
 * @param obj 要深拷贝的对象
 * @return 深拷贝后的对象
 */
fun <T : Serializable> deepCopy(obj: T): T {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(obj)
    objectOutputStream.close()

    val byteArrayInputStream = ByteArrayInputStream(byteArrayOutputStream.toByteArray())
    val objectInputStream = ObjectInputStream(byteArrayInputStream)
    @Suppress("UNCHECKED_CAST")
    return objectInputStream.readObject() as T
}


/**
 * 获取当前窗口的宽度大小类别
 * @return WindowWidthSizeClass
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun getWindowWidth(): WindowWidthSizeClass {
    val windowSizeClass = calculateWindowSizeClass(getActivity())
    return windowSizeClass.widthSizeClass
}

/**
 * 判断当前窗口是否为展开状态(折叠屏)
 * @return Boolean
 */
@Composable
fun isExpanded(): Boolean {
    if (isPreview()) return false
    return getWindowWidth() != WindowWidthSizeClass.Compact
}

/**
 * 设置状态栏颜色和图标颜色
 * @param isWhiteIcon 是否使用白色图标，默认 true
 */
@Composable
fun setStatusBarsColor(
    isWhiteIcon: Boolean = true,
): String {

    // 当前背景色
    val backgroundColor = if (isWhiteIcon) Color.Black else Color.White
    val view = LocalView.current

    LaunchedEffect(isWhiteIcon) {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        // 背景深 → 用浅色图标；背景浅 → 用深色图标
        val useLightIcons = backgroundColor.luminance() < 0.5f
        insetsController.isAppearanceLightStatusBars = !useLightIcons
    }
    return ""
}

/**
 * 设置导航栏颜色和图标颜色
 * @param isDark 是否为深色背景，默认 false
 * @param isTransparent 是否透明，默认 true
 */
@Composable
fun setNavigationBar(
    isDark: Boolean = false, // 这里的 isDark 似乎是指背景是否为深色，从而决定图标是浅色
    isTransparent: Boolean = true // 新增参数：是否透明
): String {
    val backgroundColor = if (isDark) Color.Black else Color.White
    val view = LocalView.current

    // 使用 SideEffect 或 LaunchedEffect 均可，SideEffect 在组合完成时立即执行，通常更适合 Window 设置
    SideEffect {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        // 背景深 → 用浅色图标；背景浅 → 用深色图标
        val useLightIcons = backgroundColor.luminance() < 0.5f
        insetsController.isAppearanceLightNavigationBars = !useLightIcons

        // 新增：处理透明度
        if (isTransparent) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
            }
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                window.isNavigationBarContrastEnforced = false
            }
            // 确保内容延伸
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    return ""
}


/**
 * 监听生命周期状态变化
 */
@Composable
fun OnListenLifecycle(
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onCreate: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onResume: () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    // 监听生命周期状态变化
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_PAUSE -> onPause()
                Lifecycle.Event.ON_STOP -> onStop()
                Lifecycle.Event.ON_CREATE -> onCreate()
                Lifecycle.Event.ON_DESTROY -> onDestroy()
                Lifecycle.Event.ON_RESUME -> onResume()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

/**
 * 节流函数，在指定时间间隔内最多执行一次
 * @param intervalMs 节流间隔时间（毫秒）
 * @param coroutineScope 协程作用域
 * @param action 要执行的操作
 * @return 节流后的函数
 */
fun <T> throttle(
    intervalMs: Long = 300L,
    coroutineScope: CoroutineScope,
    action: suspend (T) -> Unit
): (T) -> Unit {
    var lastExecuteTime = 0L
    var throttleJob: Job? = null

    return { param: T ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastExecuteTime >= intervalMs) {
            lastExecuteTime = currentTime
            throttleJob?.cancel()
            throttleJob = coroutineScope.launch {
                action(param)
            }
        }
    }
}

/**
 * Compose 专用的节流函数
 */
@Composable
fun <T> rememberThrottle(
    intervalMs: Long = 300L,
    action: suspend (T) -> Unit
): (T) -> Unit {
    val scope = rememberCoroutineScope()
    return remember(intervalMs) {
        throttle(intervalMs, scope, action)
    }
}

/**
 * 节流简化版本，用于无参数的点击事件
 */
@Composable
fun rememberThrottledClick(
    intervalMs: Long = 300L,
    onClick: () -> Unit
): () -> Unit {
    val scope = rememberCoroutineScope()
    return remember(intervalMs) {
        var lastExecuteTime = 0L
        {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastExecuteTime >= intervalMs) {
                lastExecuteTime = currentTime
                scope.launch { onClick() }
            }
        }
    }
}

/**
 * 防抖函数，用于防止按钮被频繁点击
 * 与节流不同，防抖会在最后一次触发后等待指定时间才执行
 */
fun <T> debounce(
    delayMs: Long = 300L,
    coroutineScope: CoroutineScope,
    action: suspend (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(delayMs)
            action(param)
        }
    }
}

/**
 * Compose 专用的防抖函数
 */
@Composable
fun <T> rememberDebounce(
    delayMs: Long = 300L,
    action: suspend (T) -> Unit
): (T) -> Unit {
    val scope = rememberCoroutineScope()
    return remember(delayMs) {
        debounce(delayMs, scope, action)
    }
}

/**
 * 防抖简化版本，用于无参数的点击事件
 */
@Composable
fun rememberDebouncedClick(
    delayMs: Long = 300L,
    onClick: () -> Unit
): () -> Unit {
    val scope = rememberCoroutineScope()
    return remember(delayMs) {
        var debounceJob: Job? = null
        {
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(delayMs)
                onClick()
            }
        }
    }
}