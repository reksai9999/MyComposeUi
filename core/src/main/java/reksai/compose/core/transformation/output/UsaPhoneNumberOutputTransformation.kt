package reksai.compose.core.transformation.output

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert

/**
 * 美国电话号码输出转换器
 * 格式：(123)456-7890
 */
class UsaPhoneNumberOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 0) insert(0, "(")
        if (length > 4) insert(4, ")")
        if (length > 8) insert(8, "-")

        // 如果大于 13 位，截断多余部分
        if (length > 13) {
            delete(13, length)
        }
    }
}