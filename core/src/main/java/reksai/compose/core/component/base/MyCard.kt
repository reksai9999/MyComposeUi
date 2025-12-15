package reksai.compose.core.component.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.component.icon.EnumArrowDirection
import reksai.compose.core.component.icon.MyIconArrow
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.BaseColors
import reksai.compose.core.theme.BaseShapes
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = BaseColors.current.white200,
    onClick: (() -> Unit)? = null,
    shape: Shape = BaseShapes.current.medium,
    content: @Composable () -> Unit,
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        modifier = Modifier
            .then(modifier)
            .then(if (onClick != null) Modifier.clickableNormalNoEffect(onClick) else Modifier)
    ) {
        content()
    }
}

@Composable
fun MyTitleCard(
    modifier: Modifier = Modifier,
    showExpandIcon: Boolean = true,
    showDivider: Boolean = true,
    title: String = "",
    initialExpand: Boolean = true,
    backgroundColor: Color = BaseColors.current.white200,
    titleStyle: TextStyle = LocalTypography.current.titleSmall,
    titleContent: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit,
) {
    var isExpanded by remember (initialExpand) { mutableStateOf(initialExpand) }
    MyCard (
        backgroundColor = backgroundColor,
        modifier = modifier
    ) {
        Column (
            modifier = Modifier.padding(contentPadding)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableNormalNoEffect {
                        if (showExpandIcon) {
                            isExpanded = !isExpanded
                        }
                    }
                    .padding(horizontal = 15.dp, vertical = 10.dp),
            ) {
                Box (
                    modifier = Modifier.weight(1f)
                ) {
                    if (titleContent != null) {
                        titleContent()
                    } else {
                        Text(
                            text = title,
                            style = titleStyle,
                            modifier = Modifier
                        )
                    }
                }

                if (showExpandIcon) {
                    MyIconArrow(
                        direction = if (isExpanded) EnumArrowDirection.Up else EnumArrowDirection.Down,
                        color = LocalColors.current.gray700,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            AnimatedVisibility(isExpanded) {
                Column {
                    if (showDivider) {
                        HorizontalDivider()
                    }
                    content()
                }

            }

        }

    }
}

@Composable
fun MyExpandedBox(
    modifier: Modifier = Modifier,
    initialExpand: Boolean = false,
    showExpandIcon: Boolean = true,
    showDivider: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    titleContent: @Composable (() -> Unit),
    titlePadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit
) {
    var isExpanded by remember (initialExpand) { mutableStateOf(initialExpand) }
    Column (
        modifier = modifier.padding(contentPadding)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(titlePadding)
                .clickableNormalNoEffect {
                    if (showExpandIcon) {
                        isExpanded = !isExpanded
                    }
                }
        ) {
            Box (
                modifier = Modifier.weight(1f)
            ) {
                titleContent()
            }

            if (showExpandIcon) {
                MyIconArrow(
                    direction = if (isExpanded) EnumArrowDirection.Up else EnumArrowDirection.Down,
                    color = LocalColors.current.gray700,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        AnimatedVisibility(isExpanded) {
            Column {
                if (showDivider) {
                    HorizontalDivider()
                }
                content()
            }

        }

    }
}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MyCardPreview() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColors.current.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        MyCard (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "内容",
                style = LocalTypography.current.bodyMedium,
                modifier = Modifier.padding(10.dp)
            )
        }

        MyTitleCard(
            title = "标题",
        ) {
            FlowRow (
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                maxItemsInEachRow = 2,
                modifier = Modifier.padding(10.dp)
            ) {
                repeat(4) {
                    MyFillButton(
                        text = "按钮 ${it + 1}",
                        shape = LocalShapes.current.small,
                        backgroundColor = LocalColors.current.gray210,
                        textColor = LocalColors.current.black200,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

    }

}