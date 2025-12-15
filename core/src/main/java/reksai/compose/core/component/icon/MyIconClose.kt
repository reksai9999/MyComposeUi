package reksai.compose.core.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import reksai.compose.core.R
import reksai.compose.core.theme.LocalColors

@Composable
fun MyIconClose(
    modifier: Modifier = Modifier,
    tint: Color = LocalColors.current.black200,
) {
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.icon_close),
        contentDescription = null,
        tint = tint,
        modifier = Modifier.then(modifier).size(24.dp)
    )
}