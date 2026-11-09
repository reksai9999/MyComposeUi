package reksai.compose.ui.ui.screen

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.component.image.MyImage
import reksai.compose.core.component.selector.MyDateRangeSelector
import reksai.compose.core.component.selector.rememberMyImageSelector
import reksai.compose.core.component.selector.rememberMyTakePictureSelector
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun SelectorScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Selector",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.red)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            LocalizedDateSelectorDemo(
                label = "中文日期选择",
                locale = Locale.SIMPLIFIED_CHINESE
            )
            LocalizedDateSelectorDemo(
                label = "韩文日期选择",
                locale = Locale.KOREA
            )
            LocalizedDateSelectorDemo(
                label = "英文日期选择",
                locale = Locale.US
            )

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
}

/** 每种语言独立保存选择结果，仅为对应弹窗提供语言环境。 */
@Composable
private fun LocalizedDateSelectorDemo(
    label: String,
    locale: Locale
) {
    var showDateSelector by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("-") }
    var endDate by remember { mutableStateOf("-") }
    val dateFormatter = remember(locale) {
        DateFormat.getDateInstance(DateFormat.SHORT, locale).apply {
            // 日期选择器返回 UTC 零点，展示时也使用 UTC，避免跨时区偏移一天。
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    Text(
        text = "$label: $startDate - $endDate",
        style = LocalTypography.current.bodySmall,
        color = LocalColors.current.black200
    )
    MyOutlineButton(
        text = label,
        onClick = {
            showDateSelector = true
        }
    )

    if (showDateSelector) {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val localizedConfiguration = remember(configuration, locale) {
            Configuration(configuration).apply {
                setLocale(locale)
            }
        }
        val localizedContext = remember(context, localizedConfiguration) {
            context.createConfigurationContext(localizedConfiguration)
        }

        // 同时覆盖配置和资源上下文，让月份、星期和默认按钮使用同一语言。
        CompositionLocalProvider(
            LocalConfiguration provides localizedConfiguration,
            LocalContext provides localizedContext,
            LocalResources provides localizedContext.resources
        ) {
            MyDateRangeSelector(
                show = true,
                onHide = {
                    showDateSelector = false
                },
                onDateSelected = { start, end ->
                    startDate = start?.let { dateFormatter.format(Date(it)) } ?: "-"
                    endDate = end?.let { dateFormatter.format(Date(it)) } ?: "-"
                }
            )
        }
    }
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