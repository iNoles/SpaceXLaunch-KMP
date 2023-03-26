plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    jvmToolchain(8)
    android()
    iosX64()
    iosArm64()

    sourceSets {
         val commonMain by getting {
            dependencies {
                api(project(":shared"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                api(compose.runtime)
                api(compose.foundation)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.material3)
            }
        }

        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                implementation("io.coil-kt:coil-compose:2.3.0")
            }
        }

        val appleMain by creating {
            dependsOn(commonMain)
        }

        val iosX64Main by getting {
            dependsOn(appleMain)
        }
        val iosArm64Main by getting {
            dependsOn(appleMain)
        }
    }
}

android {
    namespace = "com.jonathansteele.spacexlaunch.ui"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}