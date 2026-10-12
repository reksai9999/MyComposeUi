# GEMINI.md - MyComposeUi 项目指南

## 项目概览

`MyComposeUi` 是一个基于 **Jetpack Compose** 的现代化 Android 组件库项目。它旨在提供一组高质量、可复用的 UI 组件、扩展函数和工具类，以简化 Android 应用的开发。

### 核心技术栈

- **开发语言**: Kotlin (2.2.21)
- **UI 框架**: Jetpack Compose (Material 3)
- **导航框架**: **Navigation 3** (`androidx.navigation3`) - 使用状态驱动的新一代导航方案
- **图片加载**: Coil 3
- **依赖管理**: Gradle Version Catalog (`libs.versions.toml`)
- **其他关键库**:
    - `Material 3 Adaptive`: 支持折叠屏和多窗格布局
    - `Accompanist Permissions`: 权限管理
    - `Matisse`: 现代化的图片选择器
    - `ZoomImage`: 支持手势缩放的图片组件

## 项目结构

项目采用多模块架构：

- **`:core`**: 核心库模块。
    - `component/`: 包含各种 UI 组件，如 `MyAlert` (弹窗), `MySnackBar` (消息条), `MyTopBar` (顶栏) 等。
    - `extension/`: 提供丰富的扩展函数，如 `Modifier.dropShadow` (阴影), `Modifier.dashedBorder` (虚线边框) 等。
    - `theme/`: 自定义主题定义，扩展了 Material 3 的排版和颜色系统。
- **`:app`**: 演示应用模块。
    - 用于展示 `:core` 模块中各个组件的实际使用效果。
    - 包含完整的导航配置和多设备适配逻辑。

## 开发规范与最佳实践

### 1. 组件开发

- **状态提升**: 遵循 Compose 的状态提升原则，将状态管理交给调用者。
- **Slot API**: 使用 `Composable` lambda (如 `contentCompose`) 提供灵活的定制能力。
- **Modifier**: 始终将 `Modifier` 作为第一个可选参数传递，并在内部使用。

### 2. 导航 (Navigation 3)

- 项目使用了实验性的 Navigation 3 框架。
- 导航由 `RouteNavigation` 管理，通过修改 `backStackGlobal` 这个 `SnapshotStateList<NavKey>` 来触发页面跳转。
- 页面定义通过 `entryProvider` 进行注册，并支持通过 `metadata` 定义多窗格布局级别。

### 3. 修饰符扩展

- 推荐在 `core/extension/ModifierExtension.kt` 中添加通用的 UI 效果扩展。
- 复杂效果优先使用 `drawBehind` 或 `drawWithContent` 实现，以获得更好的性能。

## 构建与运行

- **环境要求**: Android Studio Jellyfish 或更高版本，JDK 17。
- **编译**: `./gradlew assembleDebug`
- **运行**: `./gradlew :app:installDebug`
- **代码检查**: 项目暂未配置显式的 lint 规则，但应遵循 Kotlin 官方代码风格。

## 常用操作

- **添加新组件**: 在 `:core` 模块的 `component` 目录下创建新包。
- **注册新页面**: 在 `app/src/main/java/reksai/compose/ui/ui/navigation/Navigation.kt` 的 `DefaultEntryProvider` 中添加 `entry`。
- **更新依赖**: 修改根目录下的 `gradle/libs.versions.toml` 文件。
