package reksai.compose.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import reksai.compose.core.config.MyGlobalConfig

private val DarkColorScheme = MyGlobalConfig.getBaseColor()

private val LightColorScheme = MyGlobalConfig.getBaseColor()
val LocalColors = staticCompositionLocalOf {
    LightColorScheme
}
val LocalTypography = staticCompositionLocalOf {
    MyGlobalConfig.getBaseTypography()
}
val LocalShapes = staticCompositionLocalOf {
    MyGlobalConfig.getBaseShapes()
}
@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTypography provides MyGlobalConfig.getBaseTypography(),
        LocalShapes provides MyGlobalConfig.getBaseShapes(),
        content = content
    )
}