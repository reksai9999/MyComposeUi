package reksai.compose.ui.ui.screen

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MySwitch
import reksai.compose.core.component.biometric.MyBiometricAuth
import reksai.compose.core.component.biometric.MyBiometricAvailability
import reksai.compose.core.component.biometric.MyBiometricType
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun BiometricPaymentScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current as FragmentActivity
    val availability = MyBiometricAuth.availability(activity)
    val fingerprintSupported = remember(activity) {
        MyBiometricAuth.isHardwareSupported(activity, MyBiometricType.Fingerprint)
    }
    val faceSupported = remember(activity) {
        MyBiometricAuth.isHardwareSupported(activity, MyBiometricType.Face)
    }
    var fingerprintEnabled by remember(fingerprintSupported) { mutableStateOf(fingerprintSupported) }
    var faceEnabled by remember(faceSupported) { mutableStateOf(faceSupported) }
    var resultText by remember(availability) {
        mutableStateOf(availability.description())
    }
    var paid by remember { mutableStateOf(false) }

    BiometricPaymentContent(
        resultText = resultText,
        paid = paid,
        fingerprintSupported = fingerprintSupported,
        fingerprintEnabled = fingerprintEnabled,
        faceSupported = faceSupported,
        faceEnabled = faceEnabled,
        paymentEnabled = availability == MyBiometricAvailability.Available &&
                (fingerprintEnabled || faceEnabled) && !paid,
        onFingerprintEnabledChange = {
            fingerprintEnabled = it
            resultText = enabledTypesStatus(fingerprintEnabled, faceEnabled)
        },
        onFaceEnabledChange = {
            faceEnabled = it
            resultText = enabledTypesStatus(fingerprintEnabled, faceEnabled)
        },
        onPay = {
            resultText = "请完成系统生物识别认证"
            val enabledTypes = enabledTypesDescription(fingerprintEnabled, faceEnabled)
            MyBiometricAuth.authenticate(
                activity = activity,
                title = "确认支付 ¥29.90",
                subtitle = "请使用${enabledTypes}完成认证",
                negativeButtonText = "exit",
                onSuccess = {
                    paid = true
                    resultText = "支付成功（模拟）"
                },
                onError = { _, message -> resultText = "认证未完成：$message" },
                onFailed = { resultText = "识别失败，请重试" },
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun BiometricPaymentContent(
    resultText: String,
    paid: Boolean,
    fingerprintSupported: Boolean,
    fingerprintEnabled: Boolean,
    faceSupported: Boolean,
    faceEnabled: Boolean,
    paymentEnabled: Boolean,
    onFingerprintEnabledChange: (Boolean) -> Unit,
    onFaceEnabledChange: (Boolean) -> Unit,
    onPay: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LocalColors.current.background),
    ) {
        MyTopBar(title = "生物识别支付")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
        ) {
            Text(
                text = if (paid) "✓" else "◎",
                style = LocalTypography.current.displayMedium,
                color = if (paid) LocalColors.current.green200 else LocalColors.current.red200,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = if (paid) LocalColors.current.green200 else LocalColors.current.red200,
                        shape = CircleShape,
                    )
                    .padding(horizontal = 24.dp, vertical = 14.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "演示商品",
                style = LocalTypography.current.titleMedium,
                color = LocalColors.current.black200,
            )
            Text(
                text = "¥29.90",
                style = LocalTypography.current.headlineMedium,
                color = LocalColors.current.black200,
                modifier = Modifier.padding(top = 8.dp),
            )

            BiometricSwitchRow(
                title = "指纹支付",
                supported = fingerprintSupported,
                checked = fingerprintEnabled,
                onCheckedChange = onFingerprintEnabledChange,
                modifier = Modifier.padding(top = 32.dp),
            )
            BiometricSwitchRow(
                title = "人脸支付",
                supported = faceSupported,
                checked = faceEnabled,
                onCheckedChange = onFaceEnabledChange,
                modifier = Modifier.padding(top = 12.dp),
            )

            Text(
                text = resultText,
                style = LocalTypography.current.bodyMedium,
                color = if (paid) LocalColors.current.green200 else LocalColors.current.gray700,
                modifier = Modifier.padding(top = 20.dp),
            )

            MyFillButton(
                text = if (paid) "已支付" else "确认支付",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                shape = LocalShapes.current.medium,
                backgroundColor = if (paymentEnabled) LocalColors.current.red200 else LocalColors.current.gray500,
                onClick = if (paymentEnabled) onPay else null,
            )
        }
    }
}

@Composable
private fun BiometricSwitchRow(
    title: String,
    supported: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            Text(
                text = title,
                style = LocalTypography.current.bodyLarge,
                color = if (supported) LocalColors.current.black200 else LocalColors.current.gray600,
            )
            if (!supported) {
                Text(
                    text = "当前设备不支持",
                    style = LocalTypography.current.bodySmall,
                    color = LocalColors.current.gray600,
                )
            }
        }
        MySwitch(
            checked = checked,
            enabled = supported,
            onCheckedChange = onCheckedChange,
        )
    }
}

private fun MyBiometricAvailability.description(): String = when (this) {
    MyBiometricAvailability.Available -> "设备支持生物识别，可以开始支付"
    MyBiometricAvailability.NoHardware -> "设备没有指纹或人脸识别硬件"
    MyBiometricAvailability.HardwareUnavailable -> "生物识别暂时不可用"
    MyBiometricAvailability.NoneEnrolled -> "请先在系统设置中录入指纹或人脸"
    MyBiometricAvailability.Unsupported -> "当前设备不支持此认证方式"
}

private fun enabledTypesDescription(fingerprintEnabled: Boolean, faceEnabled: Boolean): String {
    return when {
        fingerprintEnabled && faceEnabled -> "指纹或人脸"
        fingerprintEnabled -> "指纹"
        faceEnabled -> "人脸"
        else -> "请至少开启一种生物识别方式"
    }
}

private fun enabledTypesStatus(fingerprintEnabled: Boolean, faceEnabled: Boolean): String {
    return if (fingerprintEnabled || faceEnabled) {
        "已开启：${enabledTypesDescription(fingerprintEnabled, faceEnabled)}"
    } else {
        "请至少开启一种生物识别方式"
    }
}

@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun BiometricPaymentScreenPreview() {
    BiometricPaymentContent(
        resultText = "设备支持生物识别，可以开始支付",
        paid = false,
        fingerprintSupported = true,
        fingerprintEnabled = true,
        faceSupported = false,
        faceEnabled = false,
        paymentEnabled = true,
        onFingerprintEnabledChange = {},
        onFaceEnabledChange = {},
        onPay = {},
    )
}
