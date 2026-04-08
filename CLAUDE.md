# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概览

MyComposeUi 是一个基于 Jetpack Compose 的 Android 组件库。项目包含两个模块：

- **`:core`** — 核心库，提供可复用的 UI 组件、扩展函数和主题系统。包名 `reksai.compose.core`
- **`:app`** — 演示应用，展示 `:core` 中各组件的用法。包名 `reksai.compose.ui`

## 构建命令

```bash
./gradlew assembleDebug              # 编译
./gradlew :app:installDebug          # 安装到设备
./gradlew :core:assembleDebug        # 仅编译 core 模块
```

环境要求：JDK 17，Android Studio Jellyfish+。

## 架构要点

### 模块依赖

`:app` 依赖 `:core`。组件只在 `:core` 中定义，`:app` 仅做展示和导航配置。

### 导航 — Navigation 3 (实验性)

使用 `androidx.navigation3` 状态驱动导航方案。核心流程：

- 路由定义在 `Navigation.kt` → `DefaultEntryProvider` 中注册页面
- 通过修改 `backStackGlobal`（`SnapshotStateList<NavKey>`）触发页面跳转
- 支持 `metadata` 定义多窗格布局级别（折叠屏适配）

### 主题系统

自定义 CompositionLocal 覆盖 Material 3 默认值：

- `LocalColors` → `BaseColors`（`BaseColor.kt`）
- `LocalTypography` → Material `Typography`
- `LocalShapes` → `BaseShapes`（`BaseShapes.kt`）
- 入口：`MyTheme { ... }` 包裹根 Composable

### 组件开发规范

- **状态提升**：交互状态由调用者管理，组件自身不持有状态
- **Slot API**：通过 `contentCompose` 等 Composable lambda 提供定制插槽
- **Modifier 第一**：`Modifier` 作为第一个可选参数
- 组件位于 `core/component/` 下按功能分包：`alert`、`bar`、`button`、`dialog`、`image`、`loading`、`pager`、`selector`、`webview` 等

### 扩展函数

`core/extension/` 提供 Kotlin 扩展，重要的有：

- `ModifierExtension.kt`：`dropShadow`、`dashedBorder`、`borderCustom`、`draw9Patch`、`clickableNormal` 等 UI 效果
- 复杂效果优先使用 `drawBehind` / `drawWithContent` 实现以保证性能

### 添加新页面的流程

1. 在 `:core` 的 `component/` 下创建组件
2. 在 `:app` 的 `screen/` 下创建展示页面
3. 在 `Navigation.kt` 的 `DefaultEntryProvider` 中注册 entry

## 关键依赖

- Kotlin 2.2.21 + Compose + Material 3
- Coil 3（图片加载）、ZoomImage（图片缩放）
- Matisse（图片选择器，以 `api` 方式暴露）
- Accompanist Permissions（权限管理）
- Shimmer（骨架屏）
- Gradle Version Catalog（`gradle/libs.versions.toml`）
