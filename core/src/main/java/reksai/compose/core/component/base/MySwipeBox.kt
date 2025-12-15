package reksai.compose.core.component.base

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import reksai.compose.core.theme.LocalColors
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.ranges.coerceIn
import kotlin.ranges.rangeTo

/**
 * 可左右滑动的容器
 */
@Composable
fun MySwipeBox(
    modifier: Modifier = Modifier,
    state: AnchoredDraggableState<MySwipeAnchors> = rememberMySwipeState(),
    onDragStateChanged: (state:AnchoredDraggableState<MySwipeAnchors>) -> Unit = {},
    swipeDirection: MySwipeDirection = MySwipeDirection.EndToStart,
    startContentWidth: Dp = 0.dp,
    startContent: @Composable (RowScope.(anchoredDraggableState: AnchoredDraggableState<MySwipeAnchors>, startSwipeProgress: Float) -> Unit)? = null,
    endContentWidth: Dp = 0.dp,
    endContent: @Composable (RowScope.(anchoredDraggableState: AnchoredDraggableState<MySwipeAnchors>, endSwipeProgress: Float) -> Unit)? = null,
    content: @Composable BoxScope.(anchoredDraggableState: AnchoredDraggableState<MySwipeAnchors>, startSwipeProgress: Float, endSwipeProgress: Float) -> Unit,
) {
    val startWidthPx = with(LocalDensity.current) { startContentWidth.toPx() }
    val endWidthPx = with(LocalDensity.current) { endContentWidth.toPx() }
    val draggableAnchors : DraggableAnchors<MySwipeAnchors> = when (swipeDirection) {
        MySwipeDirection.StartToEnd -> DraggableAnchors {
            MySwipeAnchors.Start at startWidthPx
            MySwipeAnchors.Center at 0f
        }
        MySwipeDirection.EndToStart -> DraggableAnchors {
            MySwipeAnchors.Center at 0f
            MySwipeAnchors.End at -endWidthPx
        }
        MySwipeDirection.Both -> DraggableAnchors {
            MySwipeAnchors.Start at -startWidthPx
            MySwipeAnchors.Center at 0f
            MySwipeAnchors.End at endWidthPx
        }
    }

    state.updateAnchors(draggableAnchors)

    val offsetRange = when (swipeDirection) {
        MySwipeDirection.StartToEnd -> 0f..Float.POSITIVE_INFINITY
        MySwipeDirection.EndToStart -> Float.NEGATIVE_INFINITY..0f
        MySwipeDirection.Both -> Float.NEGATIVE_INFINITY..Float.POSITIVE_INFINITY
    }

    val startSwipeProgress = if (state.requireOffset() > 0f) {
        (state.requireOffset() / startWidthPx).absoluteValue
    } else 0f

    val endSwipeProgress = if (state.requireOffset() < 0f) {
        (state.requireOffset() / endWidthPx).absoluteValue
    } else 0f

    val startContentLiveWidth = startContentWidth * startSwipeProgress
    val endContentLiveWidth = endContentWidth * endSwipeProgress

    LaunchedEffect(state) {                 // ← 当 state 实例改变时重启协程
        snapshotFlow { state.targetValue }  // ← 监听目标锚点值
            .drop(1)                        // ← 跳过初始值
            .distinctUntilChanged()         // ← 只在值改变时发射
            .collect {                      // ← 收集变化
                onDragStateChanged(state)   // ← 调用回调函数
            }
    }

    Box(
        modifier = modifier.clipToBounds()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = when (swipeDirection) {
                MySwipeDirection.StartToEnd -> Arrangement.Start
                MySwipeDirection.EndToStart -> Arrangement.End
                MySwipeDirection.Both -> Arrangement.SpaceBetween
            },
            modifier = Modifier
                .fillMaxWidth()
                .matchParentSize()
        ) {

            if (swipeDirection in listOf(MySwipeDirection.StartToEnd, MySwipeDirection.Both) && startContent != null) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(startContentLiveWidth)
                        .clipToBounds()
                ) {
                    startContent(state, startSwipeProgress)
                }
            }
            if (swipeDirection in listOf(MySwipeDirection.EndToStart, MySwipeDirection.Both) && endContent != null) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(endContentLiveWidth)
                        .clipToBounds()
                ) {
                    endContent(state, endSwipeProgress)
                }
            }
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset { IntOffset(state.requireOffset().coerceIn(offsetRange).roundToInt(), 0) }
                .anchoredDraggable(state, orientation = Orientation.Horizontal)
        ) {
            content(state, startSwipeProgress, endSwipeProgress)
        }
    }

}

enum class MySwipeAnchors {
    Start,
    Center,
    End,
}

enum class MySwipeDirection {
    StartToEnd, EndToStart, Both
}

@Composable
fun rememberMySwipeState(
    initialValue: MySwipeAnchors = MySwipeAnchors.Center,
): AnchoredDraggableState<MySwipeAnchors> {
    return remember {
        AnchoredDraggableState(
            initialValue = initialValue,
        )
    }
}

@Composable
fun rememberMySwipeState(
    key: Any?,
    initialValue: MySwipeAnchors = MySwipeAnchors.Center,
): AnchoredDraggableState<MySwipeAnchors> {
    return remember (key) {
        AnchoredDraggableState(
            initialValue = initialValue,
        )
    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MySwipeBoxPreview() {
    Column (
        modifier = Modifier.background(LocalColors.current.black200)
            .padding(15.dp)
    ) {
        Box (
            modifier = Modifier.background(LocalColors.current.black200)
                .padding(15.dp)
        ) {
            val state = rememberMySwipeState(initialValue = MySwipeAnchors.Center)
            MySwipeBox(
                state = state,
                endContentWidth = 60.dp,
                endContent = { state, endSwipeProgress ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .background(LocalColors.current.green300)
                    ) {
                        Text(
                            text = "删除",
                            modifier = Modifier.requiredWidth(42.dp),
                            fontSize = 12.sp,
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )

                    }
                },
                onDragStateChanged = { state->
                }
            ) { _, _, _ ->
                // 主内容
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LocalColors.current.white200)
                        .padding(16.dp)
                ) {
                    Text("这个是列表项内容")
                }
            }
        }

        Box (
            modifier = Modifier.background(LocalColors.current.black200)
                .padding(15.dp)
        ) {
            MySwipeBox(
                state = rememberMySwipeState(key = "0", initialValue = MySwipeAnchors.End),
                endContentWidth = 60.dp,
                endContent = { state, endSwipeProgress ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .background(LocalColors.current.green300)
                    ) {
                        Text(
                            text = "删除",
                            modifier = Modifier.requiredWidth(42.dp),
                            fontSize = 12.sp,
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )

                    }
                },
                onDragStateChanged = { state->
                }
            ) { _, _, _ ->
                // 主内容
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LocalColors.current.white200)
                        .padding(16.dp)
                ) {
                    Text("这个是列表项内容")
                }
            }
        }
    }

}