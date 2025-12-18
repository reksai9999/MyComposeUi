package reksai.compose.core.component.alert

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.alert.component.MyAlertContent
import reksai.compose.core.component.dialog.MyDialog
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyAlert(
    modifier: Modifier = Modifier,
    show: Boolean,
    onHide: () -> Unit,
    showCloseIcon: Boolean = false,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,

    title: String = "",
    content: String = "",

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
    MyDialog(
        show = show,
        onHide = onHide,
        showCloseIcon = showCloseIcon,
        dismissOnClickOutside = dismissOnClickOutside,
        dismissOnBackPress = dismissOnBackPress,
        modifier = modifier
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
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 16.dp)
        )
    }
}