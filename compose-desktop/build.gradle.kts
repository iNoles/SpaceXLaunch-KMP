plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":sharedUI"))
}

// TODO: Remove once a compiler with support for >1.8.21 available
compose {
    kotlinCompilerPlugin.set(dependencies.compiler.forKotlin("1.8.20"))
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.8.21")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}