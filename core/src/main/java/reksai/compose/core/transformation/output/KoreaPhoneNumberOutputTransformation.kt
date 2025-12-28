package reksai.compose.core.transformation.output

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert

/**
 * 韩国电话号码输出转换器
 * 格式：123-4567-8901
 */
class KoreaPhoneNumberOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 3) insert(3, "-")
        if (length > 8) insert(8, "-")
        // 如果大于 11 位，截断多余部分
        if (length > 13) {
            delete(13, length)
        }
    }
}