package reksai.compose.core.component.biometric

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

enum class MyBiometricAvailability {
    Available,
    NoHardware,
    HardwareUnavailable,
    NoneEnrolled,
    Unsupported,
}

enum class MyBiometricType {
    Fingerprint,
    Face,
}

/**
 * 使用系统 BiometricPrompt 完成指纹或人脸认证。
 *
 * 组件不会读取或保存任何生物特征数据，具体使用指纹还是人脸由系统根据设备能力和用户设置决定。
 */
object MyBiometricAuth {

    private const val authenticators = BiometricManager.Authenticators.BIOMETRIC_WEAK
    private const val featureFace = "android.hardware.biometrics.face"

    fun isHardwareSupported(context: Context, type: MyBiometricType): Boolean {
        return when (type) {
            MyBiometricType.Fingerprint -> context.packageManager.hasSystemFeature(
                PackageManager.FEATURE_FINGERPRINT,
            )

            MyBiometricType.Face -> Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                    context.packageManager.hasSystemFeature(featureFace)
        }
    }

    fun availability(context: Context): MyBiometricAvailability {
        return when (BiometricManager.from(context).canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> MyBiometricAvailability.Available
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> MyBiometricAvailability.NoHardware
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> MyBiometricAvailability.HardwareUnavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> MyBiometricAvailability.NoneEnrolled
            else -> MyBiometricAvailability.Unsupported
        }
    }

    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        negativeButtonText: String = "取消",
        onSuccess: () -> Unit,
        onError: (errorCode: Int, message: CharSequence) -> Unit,
        onFailed: () -> Unit = {},
    ): Boolean {
        if (availability(activity) != MyBiometricAvailability.Available) return false

        val prompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    onFailed()
                }
            },
        )
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setAllowedAuthenticators(authenticators)
            .setNegativeButtonText(negativeButtonText)
            .setConfirmationRequired(true)
            .build()

        prompt.authenticate(promptInfo)
        return true
    }
}
