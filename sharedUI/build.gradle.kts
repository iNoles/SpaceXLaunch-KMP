plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
         val commonMain by getting {
            dependencies {
                api(project(":shared"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-RC")
                api(compose.runtime)
                api(compose.foundation)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.material3)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val appleMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
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

dependencies {
    implementation("io.coil-kt:coil-compose:2.3.0")
}