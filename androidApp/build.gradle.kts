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
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":sharedUI"))

    implementation("io.coil-kt:coil-compose:2.2.2")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.6.1")
}