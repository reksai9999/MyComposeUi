package reksai.compose.ui.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import reksai.compose.core.component.bar.MyTopBar
import reksai.compose.core.component.base.InlineSlot
import reksai.compose.core.component.base.MySuperText
import reksai.compose.core.component.button.MyOutlineButton
import reksai.compose.core.component.icon.MyIconSearch
import reksai.compose.core.component.image.MyImage
import reksai.compose.core.theme.LocalColors
import reksai.compose.core.theme.LocalTypography

@Composable
fun SuperTextScreenVM() {
    SuperTextScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun SuperTextScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        MyTopBar(
            title = "Super Text",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(LocalColors.current.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // ── 1. 纯文本 ──
            SectionTitle("纯文本（无内联内容）")
            MySuperText(
                text = "这是一段普通文本，没有插入任何内联内容。",
            )

            // ── 2. 开头插入色块 ──
            SectionTitle("开头插入色块")
            MySuperText(
                text = " 文本前面有一个红色色块",
                startContent = InlineSlot(width = 14.sp, height = 14.sp) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.Red),
                    )
                },
            )

            // ── 3. 结尾插入色块 ──
            SectionTitle("结尾插入色块")
            MySuperText(
                text = "文本后面有一个蓝色色块 ",
                endContent = InlineSlot(width = 14.sp, height = 14.sp) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.Blue),
                    )
                },
            )

            // ── 4. 前后都有内容 ──
            SectionTitle("前后都有内容")
            MySuperText(
                text = " 前面绿色，后面品红 ",
                startContent = InlineSlot(width = 14.sp, height = 14.sp) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Green),
                    )
                },
                endContent = InlineSlot(width = 20.sp, height = 14.sp) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Magenta),
                    )
                },
            )

            // ── 5. 插入 Icon 组件 ──
            SectionTitle("插入 Icon 组件")
            MySuperText(
                text = " 搜索关键词",
                startContent = InlineSlot(width = 16.sp, height = 16.sp) {
                    MyIconSearch(
                        tint = LocalColors.current.black200,
                        modifier = Modifier.fillMaxSize()
                    )
                },
            )

            // ── 6. 插入网络图片 ──
            SectionTitle("插入网络图片")
            MySuperText(
                text = " 文本中嵌入了一张网络图片",
                startContent = InlineSlot(width = 20.sp, height = 20.sp) {
                    MyImage(
                        image = "https://picsum.photos/seed/super/100",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                    )
                },
            )

            // ── 7. 动态尺寸 ──
            SectionTitle("动态尺寸（点击按钮切换大小）")
            var slotSize by remember { mutableStateOf(16.sp) }
            MySuperText(
                text = " 色块大小可以动态改变",
                startContent = InlineSlot(width = slotSize, height = slotSize) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Cyan),
                    )
                },
            )
            MyOutlineButton(
                text = if (slotSize == 16.sp) "放大到 28sp" else "缩小到 16sp",
            ) {
                slotSize = if (slotSize == 16.sp) 28.sp else 16.sp
            }

            // ── 8. 不同对齐方式 ──
            SectionTitle("垂直对齐方式")
            MySuperText(
                text = " AboveBaseline 对齐",
                startContent = InlineSlot(
                    width = 14.sp,
                    height = 14.sp,
                    align = PlaceholderVerticalAlign.AboveBaseline,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFF9800)),
                    )
                },
            )
            MySuperText(
                text = " TextTop 对齐",
                startContent = InlineSlot(
                    width = 14.sp,
                    height = 14.sp,
                    align = PlaceholderVerticalAlign.TextTop,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF4CAF50)),
                    )
                },
            )
            MySuperText(
                text = " TextBottom 对齐",
                startContent = InlineSlot(
                    width = 14.sp,
                    height = 14.sp,
                    align = PlaceholderVerticalAlign.TextBottom,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF2196F3)),
                    )
                },
            )
            MySuperText(
                text = " TextCenter 对齐",
                startContent = InlineSlot(
                    width = 14.sp,
                    height = 14.sp,
                    align = PlaceholderVerticalAlign.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF9C27B0)),
                    )
                },
            )

            // ── 9. 前后都插入图片 ──
            SectionTitle("前后都插入网络图片")
            MySuperText(
                text = " 左右两侧都有图片 ",
                startContent = InlineSlot(width = 20.sp, height = 20.sp) {
                    MyImage(
                        image = "https://picsum.photos/seed/left/100",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                    )
                },
                endContent = InlineSlot(width = 20.sp, height = 20.sp) {
                    MyImage(
                        image = "https://picsum.photos/seed/right/100",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                    )
                },
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = LocalTypography.current.bodySmall,
        color = LocalColors.current.gray660,
    )
}

@Preview(device = "id:pixel_9_pro", showBackground = true)
@Composable
private fun SuperTextScreenPreview() {
    SuperTextScreen(
        modifier = Modifier.fillMaxSize()
    )
}
