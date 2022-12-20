plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    iosX64()
    iosArm64()

    sourceSets {
         val commonMain by getting {
            dependencies {
                api(project(":shared"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
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