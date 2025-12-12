package reksai.compose.core.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class BaseShapes(
    val zero: Shape = RoundedCornerShape(size = 0.dp),
    val none: Shape = RoundedCornerShape(size = 0.dp),
    val tiny: Shape = RoundedCornerShape(size = 2.5.dp),
    val small: Shape = RoundedCornerShape(size = 5.dp),
    val medium: Shape = RoundedCornerShape(size = 10.dp),
    val large: Shape = RoundedCornerShape(size = 20.dp),
    val circle:Shape = CircleShape,
) {
    companion object {
        val current = BaseShapes()
    }
}