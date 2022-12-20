plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":sharedUI"))
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}