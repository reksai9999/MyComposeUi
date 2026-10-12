package reksai.compose.core.component.permissions

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import reksai.compose.core.component.alert.MyAlert

data class MyPermissionsState(
    val isAllPermissionsGranted: Boolean,
    val shouldShowRationale: Boolean,
    val launch: () -> Unit
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberMyPermissionsState(
    permissions: List<String>,
    alertText: String = "",
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit = {},
    confirmText: String = "ok",
    cancelText: String = "cancel",
): MyPermissionsState {
    var isShowDialog by remember { mutableStateOf(false) }

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions,
        onPermissionsResult = { permissions ->
            if (permissions.all { it.value }) {
                onPermissionsGranted()
            }
        }
    )

    if (alertText.isNotEmpty()) {
        // 显示权限对话框
        MyAlert(
            show = isShowDialog,
            content = alertText,
            onHide = {
                isShowDialog = false
            },
            onConfirm = {
                permissionState.launchMultiplePermissionRequest()
            },
            onCancel = {
                isShowDialog = false
                onPermissionsDenied()
            },
            confirmText = confirmText,
            cancelText = cancelText
        )
    }

    return MyPermissionsState(
        isAllPermissionsGranted = permissionState.allPermissionsGranted,
        shouldShowRationale = permissionState.shouldShowRationale,
        launch = {
            if (!permissionState.allPermissionsGranted) {
                if (alertText.isNotEmpty()) {
                    isShowDialog = true
                } else {
                    permissionState.launchMultiplePermissionRequest()
                }
            } else {
                onPermissionsGranted()
            }
        }
    )
}

/**
 * 相机权限
 */
@Composable
fun rememberCameraPermissionsState(
    alertText: String = "",
    confirmText: String = "ok",
    cancelText: String = "cancel",
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit = {}
): MyPermissionsState {
    val permissions = listOf(
        Manifest.permission.CAMERA
    )
    return rememberMyPermissionsState(
        permissions = permissions,
        alertText = alertText,
        confirmText = confirmText,
        cancelText = cancelText,
        onPermissionsGranted = onPermissionsGranted,
        onPermissionsDenied = onPermissionsDenied
    )
}

/**
 * 图片权限
 */
@Composable
fun rememberImagePermissionsState(
    alertText: String = "",
    confirmText: String = "ok",
    cancelText: String = "cancel",
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit = {}
): MyPermissionsState {
    val permissions = buildList {
        when {
            Build.VERSION.SDK_INT >= 33 -> {
                // 33 及以上 一般应用用系统自带的图片选择器就行了, 不用申请存储权限, google 审核会不通过
//                add(android.Manifest.permission.READ_MEDIA_IMAGES)
            }

            Build.VERSION.SDK_INT >= 29 -> {
                // sdk29 保存图片到相册：使用 MediaStore API，不需要 WRITE_EXTERNAL_STORAGE
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            else -> { // SDK 28 及以下
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    return rememberMyPermissionsState(
        permissions = permissions,
        alertText = alertText,
        confirmText = confirmText,
        cancelText = cancelText,
        onPermissionsGranted = onPermissionsGranted,
        onPermissionsDenied = onPermissionsDenied
    )
}

@Composable
fun rememberCameraImagePermissionsState(
    alertText: String = "",
    confirmText: String = "ok",
    cancelText: String = "cancel",
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit = {}
): MyPermissionsState {
    val permissions = buildList {

        //相机
        add(Manifest.permission.CAMERA)

        when {
            Build.VERSION.SDK_INT >= 33 -> {
                // 33 及以上 一般应用用系统自带的图片选择器就行了, 不用申请存储权限, google 审核会不通过
//                add(android.Manifest.permission.READ_MEDIA_IMAGES)
            }

            Build.VERSION.SDK_INT >= 29 -> {
                // sdk29 保存图片到相册：使用 MediaStore API，不需要 WRITE_EXTERNAL_STORAGE
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            else -> { // SDK 28 及以下
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    return rememberMyPermissionsState(
        permissions = permissions,
        alertText = alertText,
        confirmText = confirmText,
        cancelText = cancelText,
        onPermissionsGranted = onPermissionsGranted,
        onPermissionsDenied = onPermissionsDenied
    )
}

/**
 * 通知权限
 */
@Composable
fun rememberNotificationPermissionsState(
    alertText: String = "",
    confirmText: String = "ok",
    cancelText: String = "cancel",
    onPermissionsGranted: () -> Unit = {},
    onPermissionsDenied: () -> Unit = {}
): MyPermissionsState {
    val permissions = buildList {
        if (Build.VERSION.SDK_INT >= 33) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    return rememberMyPermissionsState(
        alertText = alertText,
        confirmText = confirmText,
        cancelText = cancelText,
        permissions = permissions,
        onPermissionsGranted = onPermissionsGranted,
        onPermissionsDenied = onPermissionsDenied
    )
}

/**
 * 下载权限
 */
@Composable
fun rememberDownloadPermissionsState(
    onPermissionsGranted: () -> Unit = {},
    onPermissionsDenied: () -> Unit = {}
): MyPermissionsState {
    val permissions = buildList {
        if (Build.VERSION.SDK_INT <= 28) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    return rememberMyPermissionsState(
        permissions = permissions,
        onPermissionsGranted = onPermissionsGranted,
        onPermissionsDenied = onPermissionsDenied
    )
}