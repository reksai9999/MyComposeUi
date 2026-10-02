# MyComposeUi

基于 Jetpack Compose 的 Android 组件库。`:core` 提供组件，`:app` 展示用法。

## GitHub Packages

发布仓库：`https://maven.pkg.github.com/reksai9999/MyComposeUi`

Maven 坐标：`io.github.reksai9999.mycomposeui:core:<版本号>`

### 自动发布

将代码推送到 `release` 分支后，`.github/workflows/auto-release.yml` 会计算下一个
`vX.Y.Z` 版本，编译并发布 release AAR、源码包、Javadoc 包和 Maven 元数据，成功后 创建相同版本的 tag 和 GitHub Release。没有 tag 时从 `0.0.1` 开始。

Actions 使用内置 `GITHUB_TOKEN`，无需另设 PAT；仓库需要允许 GitHub Actions 运行。 工作流使用 JDK 21（与 Gradle Daemon 配置一致），组件的 JVM 目标仍为 17。

### 本地发布

在用户级 `~/.gradle/gradle.properties` 中配置凭据，不要放入项目文件或提交到 Git：

```properties
gpr.user=你的GitHub用户名
gpr.key=你的PAT经典令牌
```

PAT 必须有目标仓库访问权限和 `write:packages` 权限。 也可以通过环境变量 `GITHUB_ACTOR`、`GITHUB_TOKEN` 提供凭据。

```bash
# 仅生成本地产物，检查发布结果
./gradlew :core:publishReleasePublicationToMavenLocal -PpublishVersion=0.0.1

# 上传至 GitHub Packages
./gradlew :core:publishReleasePublicationToGitHubPackagesRepository -PpublishVersion=0.0.1
```

未传 `publishVersion` 时默认使用 `0.0.1`。每次正式发布应指定新版本，避免重复发布 已有版本；本地发布的版本也应与后续自动生成的 tag 版本保持协调。

### 其他项目接入

GitHub Packages 的 Maven 包即使公开也需要认证。使用有 `read:packages` 权限且能 访问目标仓库的 PAT 经典令牌，同样配置在用户级
`~/.gradle/gradle.properties` 中。

在使用方的 `settings.gradle.kts` 中添加：

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/reksai9999/MyComposeUi")
            credentials {
                username = providers.gradleProperty("gpr.user")
                    .orElse(providers.environmentVariable("GITHUB_ACTOR")).orNull
                password = providers.gradleProperty("gpr.key")
                    .orElse(providers.environmentVariable("GITHUB_TOKEN")).orNull
            }
            content {
                includeGroup("io.github.reksai9999.mycomposeui")
            }
        }
    }
}
```

在使用方模块的 `build.gradle.kts` 中添加依赖，版本以仓库 Packages 页面为准：

```kotlin
dependencies {
    implementation("io.github.reksai9999.mycomposeui:core:0.0.1")
}
```

认证说明：[GitHub Packages Gradle 官方文档](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry)。
