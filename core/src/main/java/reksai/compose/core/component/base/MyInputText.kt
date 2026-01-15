package reksai.compose.core.component.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.TextFieldLabelScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.icon.MyIconClose
import reksai.compose.core.extension.clickableNormalNoEffect
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun MyInputText(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTypography.current.bodyMedium,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Attached(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    placeholderStyle: TextStyle = LocalTypography.current.bodySmall.copy(color = LocalColors.current.gray660),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    shape: Shape = LocalShapes.current.small,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    showClearIcon: Boolean = true,
    clearIconCompose: @Composable () -> Unit = {
        MyIconClose(
            tint = LocalColors.current.white200,
            modifier = Modifier
                .size(18.dp)
                .clip(LocalShapes.current.circle)
                .background(LocalColors.current.blackOpacity70)
                .padding(2.dp)
        )
    },
    borderColor: Color = LocalColors.current.gray300,
    backgroundColor: Color = LocalColors.current.transparent,
    errorColor: Color = LocalColors.current.redOpacity10,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors().copy(
        focusedContainerColor = backgroundColor,
        unfocusedContainerColor = backgroundColor,
        disabledContainerColor = backgroundColor,
        focusedIndicatorColor = borderColor,
        unfocusedIndicatorColor = borderColor,
        disabledIndicatorColor = borderColor,
        errorContainerColor = errorColor,
    ),
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
) {
    var isFocused by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            state = state,
            textStyle = textStyle,
            placeholder = {
                Text(
                    text = placeholder,
                    style = placeholderStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            labelPosition = labelPosition,
            label = label,
            enabled = enabled,
            readOnly = readOnly,
            shape = shape,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    AnimatedVisibility(
                        visible = showClearIcon && state.text.isNotEmpty() && enabled && !readOnly && isFocused,
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(200))
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickableNormalNoEffect { state.clearText() }
                        ) {
                            clearIconCompose()
                        }
                    }

                    suffix?.invoke()
                }
            },
            isError = isError,
            colors = colors,
            inputTransformation = inputTransformation,
            outputTransformation = outputTransformation,
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            lineLimits = lineLimits,
            contentPadding = contentPadding,
            modifier = modifier
                .onFocusChanged { isFocused = it.isFocused }
                .then(Modifier.height(36.dp))
        )
        supportingText?.let {
            it()
        }
    }


}