---
name: my-compose-ui-components
description: MyComposeUi Android 库的组件 API 手册。当用户需要使用 MyComposeUi 构建界面，或者想要查询可用组件（如 Alert, Dialog, TopBar, Button, Image 等）及修饰符 (Modifier) 的具体用法时，请激活此技能。
---

# MyComposeUi 组件使用手册

本文档详细列出了 `MyComposeUi` 核心库 (`reksai.compose.core.component.*`) 中提供的所有 UI 组件及其核心用法。本库遵循 Jetpack Compose
的最佳实践，所有带有交互状态的组件均需通过**状态提升 (State Hoisting)** 来管理。

## 1. 弹窗与对话框 (Alert & Dialog)

弹窗组件封装了 Material 3 的 Dialog 行为，并提供了更易用的插槽 API 和状态控制。

### `MyDialog` & `MyBottomDialog`

基础的对话框容器，用于自定义任意内容。

- **`MyDialog`**: 居中的标准对话框。
- **`MyBottomDialog`**: 从底部弹出的对话框（基于 `ModalBottomSheet` 行为）。

```kotlin
MyDialog(
    show = true,             // 控制显示隐藏
    onHide = { /* hide */ }, // 触发隐藏的回调
    showCloseIcon = true,    // 是否在右上角显示关闭图标
    dismissOnClickOutside = true, // 点击外部区域是否关闭
    dismissOnBackPress = true     // 物理返回键是否关闭
) {
    // Content 插槽
}
```

### `MyAlert` & `MyAlertBottom`

基于 Dialog 封装的标准提示框，提供标题、内容文本以及确认/取消按钮的快捷配置。

```kotlin
MyAlert(
    show = true,
    onHide = { /* hide */ },
    title = "提示标题",
    content = "这里是提示的具体内容。",
    confirmText = "确定",
    cancelText = "取消",
    onConfirm = { /* ... */ },
    onCancel = { /* ... */ },
    // 均支持通过 titleCompose, contentCompose, buttonCompose 传入自定义 Composable 覆盖默认样式
)
```

## 2. 基础组件 (Base Components)

### 按钮 (Button)

- **`MyButton`**: 基础按钮，支持配置背景色和边框色。
- **`MyFillButton`**: 实心按钮的快捷封装。
- **`MyOutlineButton`**: 轮廓（空心）按钮的快捷封装。

```kotlin
MyFillButton(
    text = "提交",
    shape = LocalShapes.current.circle, // 默认圆角
    modifier = Modifier.fillMaxWidth(),
    onClick = { /* ... */ }
)
```

### 输入框与开关 (Input & Switch)

- **`MyInputText`**: 基于 `TextFieldState` 的输入框，支持 `MyInputTextType` 配置（文本、密码等）。
- **`MySwitch`**: 基础的切换开关。
- **`MyCheckBox` & `MyCheckBoxText`**: 复选框，后者自带右侧文本描述。

```kotlin
MySwitch(
    checked = isChecked,
    onCheckedChange = { isChecked = it },
    thumbColor = LocalColors.current.white200
)
```

### 信息展示 (Information Display)

- **`MyCard` / `MyTitleCard`**: 带有圆角和阴影的基础卡片，`MyTitleCard` 自带标题栏和展开/收起图标。
- **`MyExpandedBox`**: 支持展开/收起动画的容器。
- **`MyBadgedBox`**: 徽标容器，支持传入 `count: Int` 或是 `badgeContent: String`，也可仅展示红点。
- **`MyColorText`**: 支持在一段文本中将部分文字着色并设置为可点击 (`clickableParts`) 的富文本组件。
- **`MyStep`**: 步骤条指示器。
- **`MyLoading`**: 局部加载遮罩层。

```kotlin
MyBadgedBox(count = 99) {
    Icon(...)
}

MyLoading(
    isLoading = state.loading,
    text = "正在加载...",
    backgroundColor = LocalColors.current.blackOpacity10
) { /* 你的内容 */ }
```

## 3. 导航栏与菜单 (Bars & Menus)

- **`MyTopBar`**: 统一的页面顶栏。

```kotlin
MyTopBar(
    title = "页面标题",
    titleStyle = LocalTypography.current.titleMedium,
    onBack = { /* 若提供会显示返回按钮 */ }
)
```

- **`MySnackBar`**: 基于 `SnackbarHostState` 封装的消息提示条。
- **`MyMenu`**: 基于 `DropdownMenu` 封装的弹出菜单，支持传入 `offset: DpOffset` 定位。

## 4. 媒体与网页 (Media & WebView)

- **`MyImage`**: 基于 Coil 封装的图片加载组件，`image` 参数支持 URL、ResourceID、Bitmap 等多种类型。
- **`MyImagePreview`**: 结合了 ZoomImage 的图片预览组件，支持手势缩放，并可以传入 `urlList` 实现多图预览滑动。
- **`MyWebView`**: 网页加载容器，支持传入 `url` 加载线上网页或传入 `data` 加载 HTML 文本。

## 5. 高级交互组件 (Advanced Interactive)

- **`MyPager`**: 分页组件，基于 `HorizontalPager`，支持传入 `items: List<T>` 并在 lambda 中渲染每一页。内置 `initPageIndex`。
- **`MySwipeBox`**: 支持侧滑交互的容器，内置 `AnchoredDraggableState<MySwipeAnchors>`，支持设定滑动方向（如 `EndToStart`）。
- **`MyDateRangeSelector`**: 日期范围选择器弹窗。

## 6. 图标库 (Icons)

内置了一系列快捷图标，避免重复编写 `ImageVector` 调用。均可通过 `tint` 修改颜色。

- `MyIconArrow` / `MyIconArrowLeft` / `MyIconArrowRight` / `MyIconArrowUp` / `MyIconArrowDown`
- `MyIconAdd` (加号)
- `MyIconClose` (关闭)
- `MyIconMinus` (减号)
- `MyIconSearch` (搜索)
- `MyIconHelp` (帮助/问号)

---

## 自定义修饰符 (Modifier Extensions)

使用本库时，应优先使用这些扩展来实现特殊 UI 效果，而不是引入其他第三方方案。

- `Modifier.clearFocusOnKeyboardDismiss()`: 当软键盘收起时，自动清除当前输入框的焦点。
- `Modifier.scrollBringIntoView(requester)`: 配合 `BringIntoViewRequester`，在组件获取焦点时将其平滑滚动到可视区域内。
- `Modifier.dropShadow(...)`: 自定义四边阴影，相比官方 `shadow` 提供对 `blur`(模糊)、`spread`(扩散)和 `offsetX/Y` 的精确控制。
- `Modifier.borderCustom(...)`: 支持独立设置上下左右四个边框不同宽度和颜色的修饰符。
- `Modifier.dashedBorder(...)`: 提供虚线边框，可配置虚线宽度 `dashWidth` 和间隙 `dashGap`。
- `Modifier.draw9Patch(context, drawableRes)`: 高效地将 `.9` 图片绘制在组件背景中。
- `Modifier.clickableNormal(...)` / `Modifier.clickableNormalNoEffect(...)`: 标准化水波纹点击事件或无感点击事件。
