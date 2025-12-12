package reksai.compose.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorScheme = BaseColors()

private val LightColorScheme = BaseColors()
val LocalColors = staticCompositionLocalOf {
    LightColorScheme
}
val LocalTypography = staticCompositionLocalOf {
    Typography()
}
val LocalShapes = staticCompositionLocalOf {
    BaseShapes()
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
        LocalTypography provides Typography(),
        LocalShapes provides BaseShapes(),
        content = content
    )
}