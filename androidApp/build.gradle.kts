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
    implementation(project(":shared"))

    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.27.1")

    val composeBom = platform("androidx.compose:compose-bom:2022.11.00")
    implementation (composeBom)
    androidTestImplementation (composeBom)

    implementation("androidx.compose.material3:material3")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.6.1")

    implementation("androidx.navigation:navigation-compose:2.5.3")
}