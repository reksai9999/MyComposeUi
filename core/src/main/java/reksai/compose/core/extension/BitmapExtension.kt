package reksai.compose.core.extension

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * 将 Bitmap 转换为 Base64 字符串
 * @param quality 压缩质量，范围 0-100
 * @param format 压缩格式，默认 JPEG
 */
fun Bitmap.toBase64(
    quality: Int = 70,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
): String {
    require(quality in 0..100) { "质量参数必须在 0-100 之间" }

    return ByteArrayOutputStream().use { outputStream ->
        if (!this.compress(format, quality, outputStream)) {
            throw kotlin.IllegalStateException("位图压缩失败")
        }
        Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
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