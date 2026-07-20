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
import reksai.compose.core.component.biometric.MyBiometricAuth
import reksai.compose.core.component.biometric.MyBiometricAvailability
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

@Composable
fun BiometricPaymentScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current as FragmentActivity
    val availability = MyBiometricAuth.availability(activity)
    var resultText by remember(availability) {
        mutableStateOf(availability.description())
    }
    var paid by remember { mutableStateOf(false) }

    BiometricPaymentContent(
        resultText = resultText,
        paid = paid,
        paymentEnabled = availability == MyBiometricAvailability.Available && !paid,
        onPay = {
            resultText = "请完成系统生物识别认证"
            MyBiometricAuth.authenticate(
                activity = activity,
                title = "确认支付 ¥29.90",
                subtitle = "请使用指纹或人脸完成认证",
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
    paymentEnabled: Boolean,
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
            ) {
                Text(
                    text = "认证方式",
                    style = LocalTypography.current.bodyMedium,
                    color = LocalColors.current.gray700,
                )
                Text(
                    text = "指纹 / 人脸",
                    style = LocalTypography.current.bodyMedium,
                    color = LocalColors.current.black200,
                )
            }

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

private fun MyBiometricAvailability.description(): String = when (this) {
    MyBiometricAvailability.Available -> "设备支持生物识别，可以开始支付"
    MyBiometricAvailability.NoHardware -> "设备没有指纹或人脸识别硬件"
    MyBiometricAvailability.HardwareUnavailable -> "生物识别暂时不可用"
    MyBiometricAvailability.NoneEnrolled -> "请先在系统设置中录入指纹或人脸"
    MyBiometricAvailability.Unsupported -> "当前设备不支持此认证方式"
}

@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun BiometricPaymentScreenPreview() {
    BiometricPaymentContent(
        resultText = "设备支持生物识别，可以开始支付",
        paid = false,
        paymentEnabled = true,
        onPay = {},
    )
}
