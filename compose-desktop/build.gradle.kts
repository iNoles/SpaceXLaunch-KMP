plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.2.2"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    mavenLocal()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":shared"))
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}