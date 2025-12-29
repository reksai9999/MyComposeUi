package reksai.compose.core.component.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyColorText(
    fullText: String,
    modifier: Modifier = Modifier,
    clickableParts: List<ClickablePart> = emptyList(),
    textAlign: TextAlign? = null,
    style: TextStyle = LocalTypography.current.titleSmall
) {
    var fullText1 = fullText
    val annotatedText = buildAnnotatedString {
        clickableParts.forEach { part ->
            append(fullText1.substringBefore(part.text))

            val linkAnnotation = LinkAnnotation.Clickable(
                tag = part.text,
                styles = TextLinkStyles(
                    style = SpanStyle(color = part.color)
                )
            ) {
                part.onClick?.invoke(part.text)
            }

            withLink(linkAnnotation) {
                append(part.text)
            }

            fullText1 = fullText1.substringAfter(part.text)
        }
        append(fullText1)
    }

    BasicText(
        text = annotatedText,
        style = style.merge(textAlign = textAlign ?: TextAlign.Unspecified),
        modifier = modifier
    )
}

data class ClickablePart(
    val text: String,
    val color: Color,
    val onClick: ((text: String) -> Unit)? = null
)

@Composable
fun MyColorText(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = LocalTypography.current.bodyMedium,
    colorText: String = "",
    color: Color = Color.Red,
    textAlign: TextAlign? = null,
    onClick: ((text: String) -> Unit)? = null,
) {

    MyColorText(
        fullText = text,
        clickableParts = if (colorText.isNotEmpty()) {
            listOf(
                ClickablePart(
                    text = colorText,
                    color = color,
                    onClick = onClick,
                )
            )
        } else {
            emptyList()
        },
        style = textStyle,
        textAlign = textAlign,
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun MyColorTextPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MyColorText(
            fullText = "This is a sample text with clickable parts.",
            clickableParts = listOf(
                ClickablePart(
                    text = "sample",
                    color = Color.Red
                ),
                ClickablePart(
                    text = "clickable",
                    color = Color.Blue
                )
            )
        )

        MyColorText(
            text = "(必填) 名字",
            colorText = "(必填)",
        )
    }

}