package reksai.compose.ui.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import reksai.compose.ui.ui.screen.AlertScreen
import reksai.compose.ui.ui.screen.CheckBoxScreen
import reksai.compose.ui.ui.screen.ColorBoxScreenVM
import reksai.compose.ui.ui.screen.ColorTextScreenVM
import reksai.compose.ui.ui.screen.DialogScreen
import reksai.compose.ui.ui.screen.ImagePreviewScreen
import reksai.compose.ui.ui.screen.ImageScreen
import reksai.compose.ui.ui.screen.InputTextScreen
import reksai.compose.ui.ui.screen.MainScreen
import reksai.compose.ui.ui.screen.MenuScreenVM
import reksai.compose.ui.ui.screen.PagerScreen
import reksai.compose.ui.ui.screen.SelectorScreen
import reksai.compose.ui.ui.screen.TabsScreen

val DefaultRoute = RouteMain
var backStackGlobal: SnapshotStateList<NavKey>? = null

private val DefaultEntryProvider: (Any) -> NavEntry<Any> = entryProvider {
    entry<RouteMain> (metadata = NavigationLevel.Level1) { MainScreen() }
    entry<RouteInputText>(metadata = NavigationLevel.Level2) { InputTextScreen() }
    entry<RouteCheckBox>(metadata = NavigationLevel.Level2) { CheckBoxScreen() }
    entry<RouteDialog> (metadata = NavigationLevel.Level2) { DialogScreen() }
    entry<RouteAlert>(metadata = NavigationLevel.Level2) { AlertScreen() }
    entry<RouteImage>(metadata = NavigationLevel.Level2) { ImageScreen() }
    entry<RouteImagePreview>(metadata = NavigationLevel.Level2) { ImagePreviewScreen(it.url, it.urls) }
    entry<RouteSelector>(metadata = NavigationLevel.Level2) { SelectorScreen() }
    entry<RoutePager>(metadata = NavigationLevel.Level2) { PagerScreen() }
    entry<RouteTabs>(metadata = NavigationLevel.Level2) { TabsScreen() }
    entry<RouteColorText>(metadata = NavigationLevel.Level2) { ColorTextScreenVM() }
    entry<RouteMenu>(metadata = NavigationLevel.Level2) { MenuScreenVM() }
    entry<RouteColorBox>(metadata = NavigationLevel.Level2) { ColorBoxScreenVM() }

}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RouteNavigation() {
    val backStack = backStackNavigation()
    val listDetailStrategy = rememberListDetailSceneStrategy<Any>(
        directive = calculatePaneDirective(isExpanded())
    )

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator() //额外的 为每个页面提供独立 ViewModelScope
        ),
        transitionSpec = { slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(targetOffsetX = { -it }) },
        popTransitionSpec = { slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it }) },
        predictivePopTransitionSpec = { slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it }) },
        sceneStrategy = listDetailStrategy,
        entryProvider = DefaultEntryProvider
    )
}

@Composable
fun backStackNavigation(): SnapshotStateList<NavKey> {
    backStackGlobal?.let {
        return it
    }
    val backStack: SnapshotStateList<NavKey> = rememberNavBackStack(DefaultRoute).toMutableStateList()
    backStackGlobal = backStack
    return backStack
}

data object NavigationLevel {
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    val Level1 = ListDetailSceneStrategy.listPane(
        detailPlaceholder = {
        }
    )

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    val Level2 = ListDetailSceneStrategy.detailPane()

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    val Level3 = ListDetailSceneStrategy.extraPane()

}

@Composable
fun calculatePaneDirective(isExpanded: Boolean): PaneScaffoldDirective {
    val screenWidth = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() }
    val maxHorizontalPartitions =  if (isExpanded) 2 else 1
    val horizontalPartitionSpacerSize = 0.dp
    val defaultPanePreferredWidth = (screenWidth / 2)

    val maxVerticalPartitions = 1
    val verticalPartitionSpacerSize = 0.dp

    return PaneScaffoldDirective(
        maxHorizontalPartitions = maxHorizontalPartitions,
        horizontalPartitionSpacerSize = horizontalPartitionSpacerSize,
        maxVerticalPartitions = maxVerticalPartitions,
        verticalPartitionSpacerSize = verticalPartitionSpacerSize,
        defaultPanePreferredWidth = defaultPanePreferredWidth,
        defaultPanePreferredHeight = screenWidth,
        excludedBounds = emptyList()
    )
}

@Composable
fun getLocalActivity(): ComponentActivity {
    val context = LocalContext.current
    return context as ComponentActivity
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun getWindowWidth(): androidx.compose.material3.windowsizeclass.WindowWidthSizeClass {
    val windowSizeClass = calculateWindowSizeClass(getLocalActivity())
    return windowSizeClass.widthSizeClass
}

/**
 * 判断当前窗口是否为展开状态(折叠屏)
 * @return Boolean
 */
@Composable
fun isExpanded(): Boolean {
    if (LocalInspectionMode.current) return false
    return getWindowWidth() != androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Compact
}