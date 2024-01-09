plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
}