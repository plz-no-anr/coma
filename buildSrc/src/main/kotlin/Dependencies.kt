import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

private const val impl = "implementation"
private const val api = "api"
private const val kapt = "kapt"
private const val debug = "debugImplementation"
private const val test = "testImplementation"
private const val androidTest = "androidTestImplementation"

inline val PluginDependenciesSpec.androidApplication: PluginDependencySpec
    get() = id("com.android.application")
inline val PluginDependenciesSpec.androidLibrary: PluginDependencySpec
    get() = id("com.android.library")
inline val PluginDependenciesSpec.kotlinAndroid: PluginDependencySpec
    get() = kotlin("android")

object AndroidX {
    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    // lifecycle
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

    object Compose {
        const val bom = "androidx.compose:compose-bom:$composeVersion"
        const val ui = "androidx.compose.ui:ui"
        const val foundation = "androidx.compose.foundation:foundation"
        const val material3 = "androidx.compose.material3:material3"
        const val material3Window = "androidx.compose.material3:material3-window-size-class"
        const val materialIcons = "androidx.compose.material:material-icons-core"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended"

        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val uiViewBinding = "androidx.compose.ui:ui-viewbinding"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val uiTest = "androidx.compose.ui:ui-test-junit4"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"

        const val activity = "androidx.activity:activity-compose:$activityVersion"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
        const val navigation = "androidx.navigation:navigation-compose:$navVersion"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

}

object JetBrains {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
}

object ThirdParty {
    const val timber = "com.jakewharton.timber:timber:$timberVersion"
}

fun DependencyHandlerScope.lifecycle() {
    impl(AndroidX.lifecycleLiveData)
    impl(AndroidX.lifecycleViewModel)
    impl(AndroidX.lifecycleRuntime)
}

fun DependencyHandlerScope.coroutines() {
    impl(JetBrains.coroutines)
    test(JetBrains.coroutineTest)
}

fun DependencyHandlerScope.compose() {
    // Compose
    impl(platform(AndroidX.Compose.bom))
    impl(AndroidX.Compose.material3)
    impl(AndroidX.Compose.material3Window)
    impl(AndroidX.Compose.materialIcons)
    impl(AndroidX.Compose.materialIconsExtended)
    impl(AndroidX.Compose.foundation)
    impl(AndroidX.Compose.ui)
    impl(AndroidX.Compose.uiTooling)
    // Android Studio Preview support
    impl(AndroidX.Compose.uiToolingPreview)
    debug(AndroidX.Compose.uiTooling)
    // UI Tests
    androidTest(AndroidX.Compose.uiTest)
    debug(AndroidX.Compose.uiTestManifest)

    impl(AndroidX.Compose.uiViewBinding)
    androidTest(platform(AndroidX.Compose.bom))

    impl(AndroidX.Compose.activity)
    impl(AndroidX.Compose.viewModel)
    impl(AndroidX.Compose.runtime)
    impl(AndroidX.Compose.navigation)
    impl(AndroidX.Compose.hiltNavigation)
}