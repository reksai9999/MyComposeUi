package reksai.compose.core.component.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyDateRangeSelector(
    show: Boolean,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = "ok",
    cancelText: String = "cancel",

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
        DatePickerDialog(
            onDismissRequest = onHide,
            shape = LocalShapes.current.medium,
            colors = DatePickerDefaults.colors().copy(
                containerColor = LocalColors.current.white200
            ),
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
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
                            val start = dateRangePickerState.selectedStartDateMillis
                            val end = dateRangePickerState.selectedEndDateMillis
                            onDateSelected(start, end)
                            onHide()
                        }
                    ) {
                        confirmCompose()
                    }



                }
            },
            modifier = modifier.scale(0.9f)
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                showModeToggle = false,
                title = null,
                headline = null,
                colors = DatePickerDefaults.colors().copy(
                    containerColor = LocalColors.current.white200,
                    dayInSelectionRangeContentColor = LocalColors.current.white200,
                    dayInSelectionRangeContainerColor = LocalColors.current.purple300
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )
        }
    }
}