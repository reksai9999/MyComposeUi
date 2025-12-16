package reksai.compose.core.component.alert

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.alert.component.MyAlertContent
import reksai.compose.core.component.dialog.MyBottomDialog
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyAlertBottom(
    show: Boolean,
    onHide: () -> Unit,
    showCloseIcon: Boolean = false,
    shouldDismissOnBackPress: Boolean = true,
    shouldDismissOnClickOutside: Boolean = true,

    title: String = "",
    content: String = "",

    modifier: Modifier = Modifier,
    titleStyle: TextStyle = LocalTypography.current.bodyMedium,
    contentStyle: TextStyle = LocalTypography.current.bodySmall,
    contentPadding: PaddingValues = PaddingValues(0.dp),

    confirmText: String = "ok",
    cancelText: String = "cancel",
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},

    titleCompose: @Composable (() -> Unit)? = null,
    contentCompose: @Composable (() -> Unit)? = null,
    buttonCompose: @Composable (() -> Unit)? = null,

    ) {
    MyBottomDialog(
        show = show,
        onHide = onHide,
        modifier = modifier,
        isDrag = false,
        showDragHandle = true,
        showCloseIcon = showCloseIcon,
        shouldDismissOnBackPress = shouldDismissOnBackPress,
        shouldDismissOnClickOutside = shouldDismissOnClickOutside,
    ) { hide ->
        MyAlertContent(
            title = title,
            content = content,
            titleStyle = titleStyle,
            contentStyle = contentStyle,
            contentPadding = contentPadding,
            confirmText = confirmText,
            cancelText = cancelText,
            onConfirm = {
                onConfirm()
                hide()
            },
            onCancel = {
                onCancel()
                hide()
            },
            titleCompose = titleCompose,
            contentCompose = contentCompose,
            buttonCompose = buttonCompose,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 30.dp)
        )
    }

}