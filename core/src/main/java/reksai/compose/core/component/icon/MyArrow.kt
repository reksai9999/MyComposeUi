package reksai.compose.core.component.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import reksai.compose.core.R

enum class EnumArrowDirection {
    Left,
    Right,
    Up,
    Down
}

@Composable
fun MyArrow(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    direction: EnumArrowDirection = EnumArrowDirection.Left,
) {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
        contentDescription = null,
        tint = color,
        modifier = modifier
            .then(
                when (direction) {
                    EnumArrowDirection.Left -> Modifier
                    EnumArrowDirection.Right -> Modifier.rotate(180f)
                    EnumArrowDirection.Up -> Modifier.rotate(90f)
                    EnumArrowDirection.Down -> Modifier.rotate(-90f)
                }
            )
    )
}

@Composable
fun MyArrowRight(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    MyArrow(
        modifier = modifier,
        color = color,
        direction = EnumArrowDirection.Right
    )
}

@Composable
fun MyArrowLeft(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    MyArrow(
        modifier = modifier,
        color = color,
        direction = EnumArrowDirection.Left
    )
}

@Composable
fun MyArrowUp(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    MyArrow(
        modifier = modifier,
        color = color,
        direction = EnumArrowDirection.Up
    )
}

@Composable
fun MyArrowDown(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    MyArrow(
        modifier = modifier,
        color = color,
        direction = EnumArrowDirection.Down
    )
}
