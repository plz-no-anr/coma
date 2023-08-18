import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    nexusPublish
}

buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

val properties = gradleLocalProperties(rootDir)

val userMap = mapOf(
    "ossrhUsername" to properties.getProperty("ossrhUsername"),
    "ossrhPassword" to properties.getProperty("ossrhPassword"),
    "sonatypeStagingProfileId" to properties.getProperty("sonatypeStagingProfileId"),
)

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(userMap["sonatypeStagingProfileId"])
            username.set(userMap["ossrhUsername"])
            password.set(userMap["ossrhPassword"])
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}