package reksai.compose.ui.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import reksai.compose.ui.ui.navigation.MyRoute

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    titleStyle: TextStyle = LocalTypography.current.titleMedium.copy(color = LocalColors.current.white200, fontWeight = FontWeight.Bold),
    showBack: Boolean = true,
    onBackClick: () -> Unit = { MyRoute.back() },
    state: ScrollState? = null,
    distance: Dp = 400.dp,// 触发回到顶部的距离
    showScrollBack: Boolean = false,
    isThrottled: Boolean = true, //防止快速点击
    startColor: Color = LocalColors.current.black200,
    endColor: Color = LocalColors.current.black200,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null
) {
    MyTopBar(
        modifier = modifier,
        title = title,
        titleStyle = titleStyle,
        showBack = showBack,
        onBackClick = onBackClick,
        state = state,
        distance = distance,
        showScrollBack = showScrollBack,
        isThrottled = isThrottled,
        startColor = startColor,
        endColor = endColor,
        leftIcon = leftIcon,
        rightIcon = rightIcon
    )
}