package reksai.compose.core.theme

import androidx.compose.ui.graphics.Color

data class BaseColors(
    val title:Color = Color(0xFFE70F0F),
    val background:Color = Color(0xFFF1F5F9),
    val bottomBarBackground:Color = Color(0xFFF3F3F3),

    val black: Color = Color(0xFF000000),
    val darkGray: Color = Color(0xFF444444),
    val gray: Color = Color(0xFF888888),
    val lightGray: Color = Color(0xFFCCCCCC),
    val white: Color = Color(0xFFFFFFFF),
    val red: Color = Color(0xFFFF0000),
    val green: Color = Color(0xFF00FF00),
    val blue: Color = Color(0xFF0000FF),
    val yellow: Color = Color(0xFFFFFF00),
    val cyan: Color = Color(0xFF00FFFF),
    val magenta: Color = Color(0xFFFF00FF),
    val transparent: Color = Color(0x00000000),

    val blackOpacity10: Color = Color(0x1A000000),
    val blackOpacity20: Color = Color(0x33000000),
    val blackOpacity30: Color = Color(0x4D000000),
    val blackOpacity40: Color = Color(0x66000000),
    val blackOpacity50: Color = Color(0x80000000),
    val blackOpacity60: Color = Color(0x99000000),
    val blackOpacity70: Color = Color(0xB3000000),
    val blackOpacity80: Color = Color(0xCC000000),
    val blackOpacity90: Color = Color(0xE6000000),


    //灰色
    val gray100: Color = Color(0xFFF6F5F9),
    val gray120: Color = Color(0xFFF1F5F9),
    val gray130: Color = Color(0xFFF2F2F2),
    val gray200: Color = Color(0xFFF3F3F3),
    val gray210: Color = Color(0xFFF0F0F0),
    val gray220: Color = Color(0xFFE9E9E9),
    val gray230: Color = Color(0xFFEEEEEE),
    val gray300: Color = Color(0xFFDCDCDC),
    val gray400: Color = Color(0xFFD6D6D6),
    val gray450: Color = Color(0xFFC5C6C9),
    val gray500: Color = Color(0xFFCCCCCC),
    val gray550: Color = Color(0xFFDDDDDD),
    val gray560: Color = Color(0xFFF8F7FA),
    val gray600: Color = Color(0xFFA0A0A0),
    val gray650: Color = Color(0xFFA9A9A9),
    val gray660: Color = Color(0xFF838383),
    val gray670: Color = Color(0xFF6D7079),
    val gray700: Color = Color(0xFF888888),
    val gray750: Color = Color(0xFF7F7F7F),
    val gray800: Color = Color(0xFF555555),

    //红色
    val red50: Color = Color(0xFFFFECEC),
    val red200: Color = Color(0xFFFF4000),
    val red300: Color = Color(0xFFFF344B),
    val red310: Color = Color(0xFFFB6405),
    val red500: Color = Color(0xFFBA0219),


    //粉色
    val pink150: Color = Color(0xFFFFEDED),
    val pink200: Color = Color(0xFFFC7CAE),

    //黄色
    val yellow50: Color = Color(0xFFFFF8F8),
    val yellow100: Color = Color(0xFFFFF6E5),
    val yellow200: Color = Color(0xFFFFEA05),
    val yellow300: Color = Color(0xFFEEAD6D),
    val yellow400: Color = Color(0xFFEE792F),

    //绿色
    var green100: Color = Color(0xFF009F94),
    var green120: Color = Color(0xFF36AB36),
    var green200: Color = Color(0xFF00A22A),
    var green300: Color = Color(0xFF009B21),

    //黑色
    var black200: Color = Color(0xFF2F2F2F),

    //白色
    val white200: Color = Color(0xFFFFFFFF),

    //蓝色
    val blue100: Color = Color(0xFF228AEA),
    val blue500: Color = Color(0xFF4380F3),
    val blue600: Color = Color(0xFF0070C0),
    val blue700: Color = Color(0xFF4B49CF),




    //紫色
    val purple200: Color = Color(0xFFB588F7),
    val purple300: Color = Color(0xFF665BA1),
    val purple400: Color = Color(0xFF7A48CE),


) {
    companion object {
        val Color = BaseColors()
        val current = Color
    }
}