import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("io.github.gradle-nexus.publish-plugin") version nexusPublishVersion
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

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}