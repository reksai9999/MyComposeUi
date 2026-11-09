package reksai.compose.core.component.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Locale

/** 显示日期范围弹窗，保留 Material 的日期选择与 UTC 时间戳规则。 */
@Composable
fun MyDateRangeSelector(
    show: Boolean,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = stringResource(android.R.string.ok),
    cancelText: String = stringResource(android.R.string.cancel),

    confirmCompose: @Composable () -> Unit = {
        MyFillButton(
            text = confirmText,
            textStyle = LocalTypography.current.bodyMedium,
            buttonPadding = PaddingValues(horizontal = 30.dp, vertical = 6.dp),
        )
    },
    cancelCompose: @Composable () -> Unit = {
        MyOutlineButton(
            text = cancelText,
            textStyle = LocalTypography.current.bodyMedium,
            color = LocalColors.current.black200,
            buttonPadding = PaddingValues(horizontal = 30.dp, vertical = 6.dp),
        )
    },

    onDateSelected: (startDateMillis: Long?, endDateMillis: Long?) -> Unit = { _, _ -> }
) {
    if (show) {
        val dateRangePickerState = rememberDateRangePickerState()
        val locale = dateRangePickerState.locale
        val weekdayLabels = remember(locale) {
            dateRangeWeekdayLabels(locale)
        }

        val localizedContext = LocalContext.current

        Dialog(
            onDismissRequest = onHide,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            // Dialog 会提供自己的 Context，恢复调用方上下文以保留局部语言资源。
            CompositionLocalProvider(LocalContext provides localizedContext) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = modifier
                            .widthIn(max = 560.dp)
                            .fillMaxWidth()
                            .heightIn(max = minOf(600.dp, maxHeight)),
                        shape = LocalShapes.current.medium,
                        color = LocalColors.current.white200
                    ) {
                        Column {
                            BoxWithConstraints(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f, fill = false)
                            ) {
                                // 七列随弹窗宽度调整，避免窄屏仍强制使用 48.dp 列宽。
                                val daySize = ((maxWidth - 24.dp) / 7).coerceIn(40.dp, 48.dp)
                                CompositionLocalProvider(
                                    LocalMinimumInteractiveComponentSize provides daySize
                                ) {
                                    Box {
                                        DateRangePicker(
                                            state = dateRangePickerState,
                                            showModeToggle = false,
                                            title = null,
                                            headline = null,
                                            colors = DatePickerDefaults.colors().copy(
                                                containerColor = LocalColors.current.white200,
                                                weekdayContentColor = Color.Transparent,
                                                dayInSelectionRangeContentColor = LocalColors.current.white200,
                                                dayInSelectionRangeContainerColor = LocalColors.current.purple300
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(max = 500.dp)
                                        )
                                        DateRangeWeekdayHeader(
                                            labels = weekdayLabels,
                                            daySize = daySize
                                        )
                                    }
                                }
                            }

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 10.dp,
                                    alignment = Alignment.End
                                ),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier.clickableNormalNoEffect {
                                        onHide()
                                    }
                                ) {
                                    cancelCompose()
                                }
                                Box(
                                    modifier = Modifier.clickableNormalNoEffect {
                                        onDateSelected(
                                            dateRangePickerState.selectedStartDateMillis,
                                            dateRangePickerState.selectedEndDateMillis
                                        )
                                        onHide()
                                    }
                                ) {
                                    confirmCompose()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/** 使用当地星期短名和一周起始日；中韩文去掉会占满日期列的公共前缀或后缀。 */
internal fun dateRangeWeekdayLabels(locale: Locale): List<String> {
    val shortWeekdays = DateFormatSymbols.getInstance(locale).shortWeekdays
    val firstDayIndex = Calendar.getInstance(locale).firstDayOfWeek - Calendar.SUNDAY
    val chineseLabels = listOf("日", "一", "二", "三", "四", "五", "六")

    return List(7) { offset ->
        val dayIndex = (firstDayIndex + offset) % 7
        when (locale.language) {
            "zh" -> chineseLabels[dayIndex]
            "ko" -> shortWeekdays[dayIndex + Calendar.SUNDAY].removeSuffix("요일")
            else -> shortWeekdays[dayIndex + Calendar.SUNDAY]
        }
    }
}

/** 显示本地化的星期短名；保留 Material 原星期行的完整无障碍描述和日期列对齐。 */
@Composable
private fun DateRangeWeekdayHeader(
    labels: List<String>,
    daySize: Dp
) {
    // 与 Material 3 日历的 12.dp 水平留白和最小 48.dp 星期行高保持一致。
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(horizontal = 12.dp)
            .clearAndSetSemantics { },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        labels.forEach { label ->
            Box(
                modifier = Modifier.size(daySize),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}
