package reksai.compose.core.component.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import reksai.compose.core.theme.LocalTypography

/**
 * 内联插槽，描述嵌入文本中的 Composable 内容及其占位尺寸。
 *
 * @param width  占位宽度（TextUnit，通常为 sp）
 * @param height 占位高度（TextUnit，通常为 sp）
 * @param align  垂直对齐方式，默认居中
 * @param content 插槽内渲染的 Composable
 */
class InlineSlot(
    val width: TextUnit,
    val height: TextUnit,
    val align: PlaceholderVerticalAlign = PlaceholderVerticalAlign.Center,
    val content: @Composable () -> Unit,
)

private const val LEADING_ID = "__super_text_leading__"
private const val TRAILING_ID = "__super_text_trailing__"

/**
 * 通用超级文本组件，支持在文本 **开头** 和 **结尾** 插入任意 Composable 内容。
 *
 * 占位符尺寸由调用者通过 [InlineSlot] 传入，可配合 `mutableStateOf` 实现动态尺寸
 * （例如图片加载完成后按比例更新宽度），组件自身不持有尺寸状态（状态提升）。
 *
 * 用法示例：
 * ```kotlin
 * var iconSize by remember { mutableStateOf(20.sp) }
 *
 * MySuperText(
 *     text = "Hello World",
 *     leading = InlineSlot(width = iconSize, height = iconSize) {
 *         Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.fillMaxSize())
 *     },
 * )
 * ```
 *
 * @param text     文本内容
 * @param modifier Modifier
 * @param style    文本样式，默认使用主题 bodyMedium
 * @param color    文本颜色，默认 [Color.Unspecified]（跟随 style）
 * @param textAlign 文本对齐
 * @param maxLines 最大行数
 * @param overflow 溢出处理
 * @param softWrap 是否软换行
 * @param startContent  开头内联插槽
 * @param endContent 结尾内联插槽
 */
@Composable
fun MySuperText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTypography.current.bodyMedium,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    startContent: InlineSlot? = null,
    endContent: InlineSlot? = null,
) {
    // 构建 AnnotatedString
    val annotatedText = buildAnnotatedString {
        if (startContent != null) {
            appendInlineContent(LEADING_ID, "[leading]")
        }
        append(text)
        if (endContent != null) {
            appendInlineContent(TRAILING_ID, "[trailing]")
        }
    }

    // 构建 inlineContent 映射
    val inlineContent = buildMap {
        startContent?.let { slot ->
            put(
                LEADING_ID,
                InlineTextContent(
                    placeholder = Placeholder(
                        width = slot.width,
                        height = slot.height,
                        placeholderVerticalAlign = slot.align,
                    ),
                ) {
                    slot.content()
                }
            )
        }
        endContent?.let { slot ->
            put(
                TRAILING_ID,
                InlineTextContent(
                    placeholder = Placeholder(
                        width = slot.width,
                        height = slot.height,
                        placeholderVerticalAlign = slot.align,
                    ),
                ) {
                    slot.content()
                }
            )
        }
    }

    Text(
        text = annotatedText,
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        softWrap = softWrap,
        inlineContent = inlineContent,
    )
}

// ─────────────────────────── Preview ───────────────────────────

@Preview(showBackground = true)
@Composable
private fun MySuperTextPreview() {
    Column(
        modifier = Modifier.size(300.dp, 400.dp),
    ) {
        // 1. 纯文本
        MySuperText(
            text = "纯文本，无内联内容",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Leading 图标
        MySuperText(
            text = " 带前缀色块的文本",
            startContent = InlineSlot(width = 16.sp, height = 16.sp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Trailing 图标
        MySuperText(
            text = "带后缀色块的文本 ",
            endContent = InlineSlot(width = 16.sp, height = 16.sp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue),
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Leading + Trailing
        MySuperText(
            text = " 前后都有色块 ",
            startContent = InlineSlot(width = 16.sp, height = 16.sp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green),
                )
            },
            endContent = InlineSlot(width = 24.sp, height = 16.sp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Magenta),
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 5. 动态尺寸模拟
        var dynamicWidth by remember { mutableStateOf(16.sp) }
        MySuperText(
            text = " 动态宽度色块（点击无效，仅演示状态驱动）",
            startContent = InlineSlot(width = dynamicWidth, height = 16.sp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan),
                )
            },
        )
    }
}
