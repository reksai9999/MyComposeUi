package reksai.compose.core.component.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.TextFieldLabelScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    textStyle: TextStyle = LocalTypography.current.bodySmall,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Attached(),
    label: @Composable (TextFieldLabelScope.() -> Unit)? = null,
    placeholder: String = "",
    placeholderStyle: TextStyle = LocalTypography.current.bodySmall.copy(color = LocalColors.current.gray660),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    shape: Shape = LocalShapes.current.small,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
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
    colors: TextFieldColors = TextFieldDefaults.colors().copy(
        focusedContainerColor = LocalColors.current.white200,
        unfocusedContainerColor = LocalColors.current.white200,
        disabledContainerColor = LocalColors.current.white200,
        focusedIndicatorColor = LocalColors.current.white200,
        unfocusedIndicatorColor = LocalColors.current.white200,
        disabledIndicatorColor = LocalColors.current.white200,
    ),
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: Int = 1,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
    modifier: Modifier = Modifier
) {
//    val scope = rememberCoroutineScope()
    TextField(
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
                horizontalArrangement = Arrangement.spacedBy(2.5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                AnimatedVisibility(
                    visible = showClearIcon && state.text.isNotEmpty(),
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
        colors = colors,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
//        lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = lineLimits, maxHeightInLines = lineLimits),
        contentPadding = contentPadding,
        modifier = modifier
            .then(Modifier.height(34.dp))
    )
}