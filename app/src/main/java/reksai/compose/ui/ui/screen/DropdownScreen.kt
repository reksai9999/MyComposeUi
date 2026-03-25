package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.selector.MyDropdown
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun DropdownScreenVM() {
    DropdownScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DropdownScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Dropdown",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            var selectedDropdownItem by remember { mutableStateOf("选项2") }
            val dropdownItems = List(200) { "选项${it + 1}" }

            Text(
                text = "下拉框: $selectedDropdownItem",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier.padding(top = 5.dp)
            )

            // 样式1：基础下拉框，使用默认的PopupProperties和样式
            Text(
                text = "样式1:",
                style = LocalTypography.current.titleSmall,
                color = LocalColors.current.black200,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            MyDropdown(
                list = dropdownItems,
                defaultValue = dropdownItems[0],
                onItemSelected = { selectedDropdownItem = it },
                properties = PopupProperties(clippingEnabled = false),
                shape = LocalShapes.current.medium,
                containerColor = LocalColors.current.background,
                anchorContent = { selectedItem, isExpanded ->
                    Row(
                        modifier = Modifier
                            .background(LocalColors.current.background),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = selectedItem ?: "请选择", color = if (isExpanded) LocalColors.current.blue else LocalColors.current.black)
                    }
                },
                itemContent = { item, isSelected ->
                    Text(
                        text = item,
                        color = if (isSelected) LocalColors.current.blue else LocalColors.current.black,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                },
                dropdownModifier = Modifier
                    .heightIn(max = 150.dp)
                    .background(LocalColors.current.white200)
                    .border(1.dp, LocalColors.current.gray, LocalShapes.current.small)
            )

            // 样式2：自定义下拉框，使用不同的PopupProperties和样式
            Text(
                text = "样式2:",
                style = LocalTypography.current.titleSmall,
                color = LocalColors.current.black200,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            MyDropdown(
                list = dropdownItems,
                defaultValue = dropdownItems[0],
                onItemSelected = { selectedDropdownItem = it },
                properties = PopupProperties(clippingEnabled = false),
                shape = LocalShapes.current.medium,
                containerColor = LocalColors.current.background,
                anchorContent = { selectedItem, isExpanded ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, LocalColors.current.gray, LocalShapes.current.small)
                            .padding(10.dp)
                    ) {
                        Text(text = selectedItem ?: "请选择", color = if (isExpanded) LocalColors.current.blue else LocalColors.current.black)
                    }
                },
                itemContent = { item, isSelected ->
                    Text(
                        text = item,
                        color = if (isSelected) LocalColors.current.blue else LocalColors.current.black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                    )
                },
                dropdownModifier = Modifier
                    .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                    .heightIn(max = 150.dp)
                    .background(LocalColors.current.white200)
                    .border(1.dp, LocalColors.current.gray, LocalShapes.current.small)
            )

            // 样式3：
            Text(
                text = "样式3:",
                style = LocalTypography.current.titleSmall,
                color = LocalColors.current.black200,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                MyDropdown(
                    list = dropdownItems,
                    defaultValue = dropdownItems[0],
                    onItemSelected = { selectedDropdownItem = it },
                    properties = PopupProperties(clippingEnabled = false),
                    shape = LocalShapes.current.medium,
                    containerColor = LocalColors.current.background,
                    anchorContent = { selectedItem, isExpanded ->
                        Row(
                            modifier = Modifier
                                .background(LocalColors.current.background),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedItem ?: "请选择", color = if (isExpanded) LocalColors.current.blue else LocalColors.current.black)
                        }
                    },
                    itemContent = { item, isSelected ->
                        Text(
                            text = item,
                            color = if (isSelected) LocalColors.current.blue else LocalColors.current.black,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    },
                    dropdownModifier = Modifier
                        .heightIn(max = 150.dp)
                        .background(LocalColors.current.white200)
                        .border(1.dp, LocalColors.current.gray, LocalShapes.current.small)
                )
            }


        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun DropdownScreenPreview() {
    DropdownScreen(
        modifier = Modifier.fillMaxSize()
    )
}