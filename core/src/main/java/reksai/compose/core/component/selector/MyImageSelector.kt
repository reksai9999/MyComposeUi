package reksai.compose.core.component.selector

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import github.leavesczy.matisse.CoilImageEngine
import github.leavesczy.matisse.FileProviderCaptureStrategy
import github.leavesczy.matisse.Matisse
import github.leavesczy.matisse.MatisseContract
import github.leavesczy.matisse.MediaResource
import github.leavesczy.matisse.MediaType
import github.leavesczy.matisse.SmartCaptureStrategy
import reksai.compose.core.component.permissions.rememberImagePermissionsState
import reksai.compose.core.config.MyGlobalConfig

data class MyImageSelectorState(
    val launch: () -> Unit
)

/**
 * 图片选择器状态
 */
@Composable
fun rememberMyImageSelector(
    maxSelectableCount: Int = 1,
    matisse: Matisse = Matisse(
        maxSelectable = maxSelectableCount,
        imageEngine = CoilImageEngine(),
        mediaType = MediaType.ImageOnly,
        captureStrategy = SmartCaptureStrategy(
            FileProviderCaptureStrategy(
                authority = MyGlobalConfig.getFileProvider()
            )
        )
    ),
    onHandlerMediaResource: (List<MediaResource>) -> Unit,
): MyImageSelectorState {
    fun mediaResource(uri: @JvmSuppressWildcards Uri): MediaResource = MediaResource(
        path = uri.path ?: "",
        name = uri.lastPathSegment ?: "",
        mimeType = uri.path?.let { path ->
            when {
                path.endsWith("png", true) -> "image/png"
                path.endsWith("jpg", true) || path.endsWith("jpeg", true) -> "image/jpeg"
                path.endsWith("gif", true) -> "image/gif"
                else -> "image/*"
            }
        } ?: "image/*",
        uri = uri,
        size = 0L,
    )

    val pickerLauncher =
        rememberLauncherForActivityResult(contract = MatisseContract()) { result: List<MediaResource>? ->
            result?.let {
                onHandlerMediaResource(it)
            }
        }

    val pickerLauncher33 = if (maxSelectableCount > 1) {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = maxSelectableCount)
        ) { uris ->
            if (uris.isNotEmpty()) {
                val mediaResources = uris.map { uri ->

                    mediaResource(uri)
                }
                onHandlerMediaResource(mediaResources)
            }
        }
    } else {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let {
                val mediaResources = listOf(mediaResource(it))
                onHandlerMediaResource(mediaResources)
            }
        }
    }


    val permissionState = rememberImagePermissionsState(
        onPermissionsGranted = {
            if (Build.VERSION.SDK_INT >= 33) {
                pickerLauncher33.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                pickerLauncher.launch(matisse)
            }
        }
    )
    return MyImageSelectorState(
        launch = {
            permissionState.launch()
        }
    )
}