package reksai.compose.ui.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object RouteMain : NavKey

@Serializable
data object RouteCheckBox : NavKey

/**
 * Dialog 路由
 */
@Serializable
data object RouteDialog : NavKey

/**
 */
@Serializable
data object RouteAlert : NavKey

