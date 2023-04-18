plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    iosX64 {
        binaries.executable()
    }
    iosArm64 {
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":sharedUI"))
            }
        }

        val uikitMain by creating {
            dependsOn(commonMain)
        }

        val iosX64Main by getting {
            dependsOn(uikitMain)
        }

        val iosArm64Main by getting {
            dependsOn(uikitMain)
        }
    }
}

compose.experimental {
    uikit.application {
        bundleIdPrefix = "com.jonathansteele"
        projectName = "SpaceXLaunch"
    }
}