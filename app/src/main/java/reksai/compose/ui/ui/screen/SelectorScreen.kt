package reksai.compose.ui.ui.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.component.image.MyImage
import reksai.compose.core.component.selector.MyDateRangeSelector
import reksai.compose.core.component.selector.rememberMyImageSelector
import reksai.compose.core.component.selector.rememberMyTakePictureSelector
import reksai.compose.core.extension.toDateShortString
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography

@Composable
fun SelectorScreen(
    modifier: Modifier = Modifier,
) {
    var showDateSelector by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("-") }
    var endDate by remember { mutableStateOf("-") }

    //


    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Selector",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
        ) {
            Text(
                text = "日期选择: $startDate - $endDate",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier
            )
            MyOutlineButton(
                text = "日期选择",
            ) {
                showDateSelector = true
            }

            Text(
                text = "图片选择",
                style = LocalTypography.current.bodySmall,
                color = LocalColors.current.black200,
                modifier = Modifier
            )
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            val imageSelector = rememberMyImageSelector(
                onHandlerMediaResource = { list ->
                    // 处理选择的图片资源
                    imageUri = list.first().uri
                }
            )
            val takeSelector = rememberMyTakePictureSelector() {
                // 处理拍照的图片资源
                imageUri = it.first().uri
            }
            MyImage(
                image = imageUri ?: "https://picsum.photos/200",
                modifier = Modifier.size(120.dp, 150.dp)
            )
            MyOutlineButton(
                text = "选择图片",
                color = LocalColors.current.blue
            ) {
                imageSelector.launch()
            }

            MyOutlineButton(
                text = "拍照",
                color = LocalColors.current.blue
            ) {
                takeSelector.launch()
            }


        }
    }

    MyDateRangeSelector(
        show = showDateSelector,
        onHide = {
            showDateSelector = false
        },
        onDateSelected = { start, end ->
            startDate = start?.toDateShortString() ?: ""
            endDate = end?.toDateShortString() ?: ""
        }
    )

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun SelectorScreenPreview() {
    SelectorScreen(
        modifier = Modifier.fillMaxSize()
    )
}