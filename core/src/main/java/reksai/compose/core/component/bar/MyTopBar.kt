package reksai.compose.core.component.bar

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import reksai.compose.core.component.box.MyColorBox
import reksai.compose.core.component.icon.EnumArrowDirection
import reksai.compose.core.component.icon.MyIconArrow
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.extension.rememberThrottledClick
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    titleStyle: TextStyle = LocalTypography.current.titleMedium.copy(color = LocalColors.current.white200, fontWeight = FontWeight.Bold),
    showBack: Boolean = true,
    onBackClick: () -> Unit = {  },
    state: ScrollState? = null,
    distance: Dp = 400.dp,// 触发回到顶部的距离
    showScrollBack: Boolean = false,
    isThrottled: Boolean = true, //防止快速点击
    startColor: Color = LocalColors.current.black200,
    endColor: Color = LocalColors.current.black200,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    val px = with(LocalDensity.current) {
        distance.roundToPx()
    }
    val isShowScrollBack = remember {
        derivedStateOf {
            (state?.value ?: 0) > px
        }
    }

    /**
     * 防止快速点击
     */
    val throttledClick = if (isThrottled) rememberThrottledClick {
        onBackClick()
    } else {
        onBackClick
    }

    MyColorBox(
        contentAlignment = Alignment.BottomCenter,
        startColor = startColor,
        endColor = endColor,
        modifier = modifier.zIndex(Float.MAX_VALUE)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 10.dp)

        ) {
            Box (
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                if (leftIcon != null) {
                    leftIcon()
                } else {
                    if (showBack) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(50.dp)
                        ) {
                            MyIconArrow(
                                direction = EnumArrowDirection.Left,
                                color = LocalColors.current.white200,
                                modifier = Modifier
                                    .clickableNormalNoEffect(throttledClick)
                            )
                        }

                    } else {
                        Spacer(modifier = Modifier.width(50.dp))
                    }
                }
            }

            Text(
                text = title,
                style = titleStyle,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.Center)
                    .padding(vertical = 10.dp)
                    .then(
                        if (state != null) {
                            Modifier.clickableNormalNoEffect {
                                scope.launch {
                                    state.animateScrollTo(0)
                                }
                            }
                        } else {
                            Modifier
                        }
                    )
            )

            Box (
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                if (rightIcon != null) {
                    rightIcon()
                } else {
                    Spacer(modifier = Modifier.width(50.dp))
                }
            }

        }

        if (showScrollBack && isShowScrollBack.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(30.dp)
                    .offset(y = (40).dp)
                    .clip(LocalShapes.current.circle)
                    .background(LocalColors.current.black200)
                    .clickableNormalNoEffect {
                        scope.launch {
                            state?.animateScrollTo(0)
                        }
                    }
            ) {
                MyIconArrow(
                    direction = EnumArrowDirection.Up,
                    color = LocalColors.current.white200,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }

    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyTitleBarPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MyTopBar(title = "标题")
    }
}