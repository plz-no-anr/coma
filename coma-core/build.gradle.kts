plugins {
    androidLibrary
    kotlinAndroid
    `maven-publish`
    signing
}

android {
    namespace = "plznoanr.coma.core"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        minSdk = Config.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    api(AndroidX.lifecycleViewModel)
    api(AndroidX.Compose.runtime)
    implementation(ThirdParty.timber)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

val properties = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
val signingMap = mapOf(
    "signing.keyId" to properties.getProperty("signing.keyId"),
    "signing.password" to properties.getProperty("signing.password"),
    "signing.key" to properties.getProperty("signing.key")
)

val androidSourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs,)
}

android.publishing.singleVariant("release")

group = PUBLISH_GROUP_ID
version = PUBLISH_VERSION

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

signing {
    useInMemoryPgpKeys(
        signingMap["signing.keyId"],
        signingMap["signing.key"],
        signingMap["signing.password"]
    )
    sign(publishing.publications)
}