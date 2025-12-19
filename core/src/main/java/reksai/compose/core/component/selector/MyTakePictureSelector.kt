package reksai.compose.core.component.selector

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import github.leavesczy.matisse.FileProviderCaptureStrategy
import github.leavesczy.matisse.MatisseCapture
import github.leavesczy.matisse.MatisseCaptureContract
import github.leavesczy.matisse.MediaResource
import github.leavesczy.matisse.SmartCaptureStrategy
import reksai.compose.core.component.permissions.rememberCameraPermissionsState
import reksai.compose.core.config.MyGlobalConfig

data class MyTakePictureSelectorState(
    val launch: () -> Unit
)

/**
 * 拍照选择器状态
 */
@Composable
fun rememberMyTakePictureSelector(
    matisse: MatisseCapture = MatisseCapture(
        captureStrategy = SmartCaptureStrategy(
            FileProviderCaptureStrategy(
                authority = MyGlobalConfig.getFileProvider()
            )
        )
    ),
    onHandlerMediaResource: (List<MediaResource>) -> Unit,
): MyTakePictureSelectorState {
    val takePictureLauncher =
        rememberLauncherForActivityResult(contract = MatisseCaptureContract()) { result ->
            result?.let {
                onHandlerMediaResource(listOf(it))
            }
        }
    val permissionState = rememberCameraPermissionsState(
        onPermissionsGranted = {
            takePictureLauncher.launch(matisse)
        }
    )
    return MyTakePictureSelectorState(
        launch = {
            permissionState.launch()
        }
    )
}