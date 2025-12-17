package reksai.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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
    setSystemBarColor()
    RouteNavigation()
}