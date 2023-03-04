plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.8.10"
    id("com.rickclephas.kmp.nativecoroutines")
}

kotlin {
    jvm()
    jvmToolchain(11)
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("com.github.kittinunf.fuel:fuel-kotlinx-serialization:3.0.0-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val appleMain by creating {
            dependsOn(commonMain)
        }
        val appleTest by creating {
            dependsOn(commonTest)
        }
        val iosX64Main by getting {
            dependsOn(appleMain)
        }
        val iosArm64Main by getting {
            dependsOn(appleMain)
        }
        val macosArm64Main by getting {
            dependsOn(appleMain)
        }
        val macosX64Main by getting {
            dependsOn(appleMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(appleMain)
        }

        val iosX64Test by getting {
            dependsOn(appleMain)
        }
        val iosArm64Test by getting {
            dependsOn(appleMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(appleTest)
        }
        val macosX64Test by getting {
            dependsOn(appleTest)
        }
        val macosArm64Test by getting {
            dependsOn(appleTest)
        }
    }
}

android {
    namespace = "com.jonathansteele.spacexlaunch"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}