package reksai.compose.core.extension

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Base64
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

/**
 * 将 Bitmap 转换为 Base64 字符串
 * @param quality 压缩质量，范围 0-100
 * @param is1080p 是否将图片宽度缩放到 1080px，高度按比例缩放
 * @param format 压缩格式，默认 JPEG
 */
fun Bitmap.toBase64(
    quality: Int = 70,
    is1080p: Boolean = false,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
): String {
    require(quality in 0..100) { "质量参数必须在 0-100 之间" }

    val bitmapForCompress = if (is1080p && width != 1080) {
        val targetHeight = (height * (1080f / width)).roundToInt().coerceAtLeast(1)
        this.scale(1080, targetHeight)
    } else {
        this
    }

    return ByteArrayOutputStream().use { outputStream ->
        try {
            if (!bitmapForCompress.compress(format, quality, outputStream)) {
                throw kotlin.IllegalStateException("位图压缩失败")
            }
            Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        } finally {
            if (bitmapForCompress != this) {
                bitmapForCompress.recycle()
            }
        }
    }
}

/**
 * 旋转图片
 * @param degrees 旋转角度
 */
fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

/**
 * 翻转图片
 * @param horizontal 是否水平翻转
 * @param vertical 是否垂直翻转
 */
fun Bitmap.flip(horizontal: Boolean, vertical: Boolean): Bitmap {
    val matrix = Matrix().apply {
        postScale(
            if (horizontal) -1f else 1f,
            if (vertical) -1f else 1f,
            width / 2f,
            height / 2f
        )
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
