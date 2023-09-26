plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.1") // when changing, remember to change version in Versions.kt in buildSrc module
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21") // when changing, remember to change version in Versions.kt in buildSrc module
}