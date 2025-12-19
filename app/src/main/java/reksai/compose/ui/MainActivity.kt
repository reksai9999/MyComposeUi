package reksai.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MySnackBar
import reksai.compose.ui.ui.navigation.RouteNavigation
import reksai.compose.ui.utils.setSystemBarColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityScreen()
        }
    }
}

@Composable
fun ActivityScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        setSystemBarColor()
        RouteNavigation()
        /**
         * 全局提示组件
         */
        MySnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-120).dp)
        )
    }

}