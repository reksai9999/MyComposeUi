package reksai.compose.core.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

fun Uri.toBitmap(context: Context): Bitmap? {
    return try {
        val bitmap = context.contentResolver.openInputStream(this)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        } ?: return null

        // 读取 EXIF 信息获取图片方向
        val exif = context.contentResolver.openInputStream(this)?.use { inputStream ->
            ExifInterface(inputStream)
        }

        val orientation = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        ) ?: ExifInterface.ORIENTATION_NORMAL

        // 根据 EXIF 方向旋转图片
        val rotatedBitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> bitmap.rotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> bitmap.rotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> bitmap.rotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> bitmap.flip(horizontal = true, vertical = false)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> bitmap.flip(horizontal = false, vertical = true)
            ExifInterface.ORIENTATION_TRANSPOSE -> bitmap.rotate(90f).flip(horizontal = true, vertical = false)
            ExifInterface.ORIENTATION_TRANSVERSE -> bitmap.rotate(270f).flip(horizontal = true, vertical = false)
            else -> bitmap
        }

        // 如果创建了新的 bitmap，释放原来的
        if (rotatedBitmap != bitmap) {
            bitmap.recycle()
        }

        rotatedBitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Uri.toBase64(
    context: Context,
    quality: Int = 70,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
): String? {
    val bitmap = this.toBitmap(context) ?: return null
    return bitmap.toBase64(
        quality = quality,
        format = format
    )
}