package reksai.compose.ui.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object RouteMain : NavKey

@Serializable
data object RouteCheckBox : NavKey

@Serializable
data object RouteInputText : NavKey


/**
 * Dialog 路由
 */
@Serializable
data object RouteDialog : NavKey

@Serializable
data object RouteAlert : NavKey

/**
 * Image 路由
 */
@Serializable
data object RouteImage : NavKey

/**
 * ImagePreview 路由
 */
@Serializable
data class RouteImagePreview(
    val url: String,
    val urls: List<String>
) : NavKey

/**
 * RouteSelector 路由
 */
@Serializable
data object RouteSelector : NavKey

/**
 * RoutePager 路由
 */
@Serializable
data object RoutePager : NavKey

/**
 * RouteTabs 路由
 */
@Serializable
data object RouteTabs : NavKey

/**
 * RouteColorText 路由
 */
@Serializable
data object RouteColorText : NavKey

/**
 * RouteMenu 路由
 */
@Serializable
data object RouteMenu : NavKey

/**
 * RouteColorBox 路由
 */
@Serializable
data object RouteColorBox : NavKey

/**
 * RouteBadge 路由
 */
@Serializable
data object RouteBadge : NavKey

/**
 * RouteExtension 路由
 */
@Serializable
data object RouteExtension : NavKey








