package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.MyMenu
import reksai.compose.core.component.button.MyFillButton
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalShapes
import reksai.compose.core.theme.LocalTypography

val menuList = listOf(
    "menu 1",
    "menu 2",
    "menu 3",
    "menu 4",
    "menu 5",
)

val menuList2 = listOf(
    "menu 1",
    "menu 2",
    "menu 3",
    "menu 4",
    "menu 5",
    "menu 1",
    "menu 2",
    "menu 3",
    "menu 4",
    "menu 5",
    "menu 1",
    "menu 2",
    "menu 3",
    "menu 4",
    "menu 5",
    "menu 1",
    "menu 2",
    "menu 3",
    "menu 4",
    "menu 5",
)

@Composable
fun MenuScreenVM(
) {
    MenuScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
) {
    var showTipMenu by remember { mutableStateOf(false) }
    var showNormalMenu by remember { mutableStateOf(false) }
    var showNormalMenu1 by remember { mutableStateOf(false) }
    var showNormalMenu2 by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Menu",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
        ) {

            Row(
                modifier = Modifier
                    .weight(7f)
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(1f)
                        .background(LocalColors.current.blue)
                ) {
                    MyFillButton(
                        "tip Menu",
                    ) {
                        showTipMenu = true
                    }

                    MyMenu(
                        show = showTipMenu,
                        onHide = { showTipMenu = false },
                        modifier = Modifier
                    ) {
                        Box(
                            modifier = Modifier
                                .background(LocalColors.current.blackOpacity80, shape = LocalShapes.current.medium)
                                .padding(15.dp)
                        ) {
                            Text(
                                text = "这是一个提示内容, 简单的提示信息展示.",
                                style = LocalTypography.current.bodySmall,
                                color = LocalColors.current.white200,
                                modifier = Modifier
                            )
                        }
                    }
                }


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .background(LocalColors.current.green)
                ) {
                    MyFillButton(
                        "normal Menu",
                    ) {
                        showNormalMenu = true
                    }

                    // 多加一层 Box 为了调整 menu 位置
                    Box {
                        MyMenu(
                            show = showNormalMenu,
                            onHide = { showNormalMenu = false },
                            selected = "",
                            list = menuList,
                            modifier = Modifier
                                .background(LocalColors.current.blackOpacity80, shape = LocalShapes.current.medium)
                                .padding(15.dp)
                        ) { item, index, isSelected ->
                            Text(
                                text = item,
                                style = LocalTypography.current.bodySmall,
                                color = LocalColors.current.white200,
                                modifier = Modifier
                            )
                        }
                    }


                }


            }


            Row(
                modifier = Modifier
                    .weight(7f)
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .background(LocalColors.current.blue)
                ) {
                    MyFillButton(
                        "normal Menu ",
                    ) {
                        showNormalMenu1 = true
                    }

                    // 多加一层 Box 为了调整 menu 位置
                    MyMenu(
                        show = showNormalMenu1,
                        onHide = { showNormalMenu1 = false },
                        selected = "",
                        list = menuList2,
                        modifier = Modifier
                            .height(200.dp)
                            .background(LocalColors.current.blackOpacity80, shape = LocalShapes.current.medium)
                            .padding(5.dp)
                    ) { item, index, isSelected ->
                        Text(
                            text = item,
                            style = LocalTypography.current.bodySmall,
                            color = LocalColors.current.white200,
                            modifier = Modifier
                        )
                    }


                }


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .background(LocalColors.current.green)
                ) {
                    MyFillButton(
                        "normal Menu",
                    ) {
                        showNormalMenu2 = true
                    }

                    // 多加一层 Box 为了调整 menu 位置
                    Box {
                        MyMenu(
                            show = showNormalMenu2,
                            onHide = { showNormalMenu2 = false },
                            selected = "",
                            list = menuList2,
                            offset = DpOffset(0.dp, 100.dp),
                            modifier = Modifier
                                .height(100.dp)
                                .background(LocalColors.current.blackOpacity80, shape = LocalShapes.current.medium)
                                .padding(15.dp)
                        ) { item, index, isSelected ->
                            Text(
                                text = item,
                                style = LocalTypography.current.bodySmall,
                                color = LocalColors.current.white200,
                                modifier = Modifier
                            )
                        }
                    }


                }


            }


        }
    }

}

//@PreviewFontScale
//@PreviewScreenSizes
@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun MenuScreenPreview() {
    MenuScreen(
        modifier = Modifier.fillMaxSize()
    )
}