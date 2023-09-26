import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension

plugins {
    `maven-publish`
    signing
}

group = PUBLISH_GROUP_ID
version = PUBLISH_VERSION

val androidSourceJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(project.extensions.getByType<BaseExtension>().sourceSets.getByName("main").java.srcDirs)
    }

(project as ExtensionAware).extensions.configure<LibraryExtension>("android") {
    publishing.singleVariant("release")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                if (project.plugins.hasPlugin("com.android.library")) {
                    from(components["release"])
                } else {
                    from(components["java"])
                }
                artifact(androidSourceJar.get())
                groupId = PUBLISH_GROUP_ID
                artifactId = PUBLISH_ARTIFACT_ID
                version = PUBLISH_VERSION
                // Mostly self-explanatory metadata
                pom {
                    name.set("coma")
                    description.set("Compose MVI Android")
                    url.set("https://github.com/plz-no-anr/coma")
                    licenses {
                        license {
                            name.set("MIT license")
                            url.set("https://opensource.org/license/mit")
                        }
                    }
                    developers {
                        developer {
                            id.set("plz-no-anr")
                            name.set("SanggunPark")
                            email.set("psgxxx@naver.com")
                        }
                    }
                    // Version control info
                    scm {
                        connection.set("scm:git:github.com/plz-no-anr/coma.git")
                        developerConnection.set("scm:git:ssh://github.com/plz-no-anr/coma.git")
                        url.set("https://github.com/plz-no-anr/coma/tree/main")
                    }
                }

            }

        }
    }
}

val properties = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
val signingMap = mapOf(
    "signing.keyId" to properties.getProperty("signing.keyId"),
    "signing.password" to properties.getProperty("signing.password"),
    "signing.key" to properties.getProperty("signing.key")
)

signing {
    useInMemoryPgpKeys(
        signingMap["signing.keyId"],
        signingMap["signing.key"],
        signingMap["signing.password"]
    )
    sign(publishing.publications)
}