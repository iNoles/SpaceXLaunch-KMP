plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.jonathansteele.spacexlaunch.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.jonathansteele.spacexlaunch.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":sharedUI"))

    implementation("io.coil-kt:coil-compose:2.2.2")

    val composeBom = platform("androidx.compose:compose-bom:2022.12.00")
    implementation (composeBom)
    androidTestImplementation (composeBom)

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.6.1")
}