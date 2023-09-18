import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.version
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
inline val PluginDependenciesSpec.kotlinSerialization: PluginDependencySpec
    get() = kotlin("plugin.serialization")

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

    object Test {
        const val testCore = "androidx.test:core:$androidXTestVersion"
        const val testCoreKtx = "androidx.test:core-ktx:$androidXTestVersion"
        const val testRunner = "androidx.test:runner:$testRunnerVersion"
        const val testRules = "androidx.test:rules:$testRulesVersion"
        const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:$espressoVersion"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:$espressoVersion"
        const val espressoAccessibility = "androidx.test.espresso:espresso-accessibility:$espressoVersion"
        const val espressoWeb = "androidx.test.espresso:espresso-web:$espressoVersion"
        const val espressoIdling = "androidx.test.espresso.idling:idling-concurrent:$espressoVersion"
        //    // To use the JUnit Extension APIs
        const val junit = "androidx.test.ext:junit:$testJunitVersion"
        //    // Kotlin extensions for androidx.test.ext.junit
        const val junitKtx = "androidx.test.ext:junit-ktx:$testJunitVersion"
        const val truth = "androidx.test.ext:truth:$truthVersion"
    }

}

object JetBrains {
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
    const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationJsonVersion"
}

object Squareup {
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    const val okHttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:$okHttpUrlConnectorVersion"
}

object ThirdParty {

    object Kotest {
        const val runner = "io.kotest:kotest-runner-junit5:$kotestVersion"
        const val assertions = "io.kotest:kotest-assertions-core:$kotestVersion"
        const val property = "io.kotest:kotest-property:$kotestVersion"
    }

    const val timber = "com.jakewharton.timber:timber:$timberVersion"

    const val mockk = "io.mockk:mockk:$mockkVersion"
}

object UnitTest {
    const val junit = "junit:junit:$junitVersion"
}

fun DependencyHandlerScope.lifecycle() {
    impl(AndroidX.lifecycleLiveData)
    impl(AndroidX.lifecycleViewModel)
    impl(AndroidX.lifecycleRuntime)
}

fun DependencyHandlerScope.coroutines() {
    impl(JetBrains.coroutine)
    test(JetBrains.coroutineTest)
}

fun DependencyHandlerScope.androidXTest() {
// AndroidX Test
// Core library
// To use the androidx.test.core APIs
    androidTest(AndroidX.Test.testCore)
    androidTest(AndroidX.Test.testCoreKtx)
    androidTest(AndroidX.Test.testRunner)
    androidTest(AndroidX.Test.testRules)

    androidTest(AndroidX.Test.espressoCore)
    androidTest(AndroidX.Test.espressoContrib)
    androidTest(AndroidX.Test.espressoIntents)
    androidTest(AndroidX.Test.espressoAccessibility)
    androidTest(AndroidX.Test.espressoWeb)
    androidTest(AndroidX.Test.espressoIdling)

    androidTest(AndroidX.Test.junit)
    androidTest(AndroidX.Test.junitKtx)
    androidTest(AndroidX.Test.truth)

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


fun DependencyHandlerScope.kotest() {
    forDependecies(
        configurationName = test,
        list = listOf(
            ThirdParty.Kotest.runner,
            ThirdParty.Kotest.assertions,
            ThirdParty.Kotest.property
        )
    )
}

fun DependencyHandlerScope.forDependecies(
    configurationName: String = api,
    list: List<String>
) {
    list.forEach {
        configurationName(it)
    }
}
