plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application") version "8.0.0-alpha09" apply false
    id("com.android.library") version "8.0.0-alpha08" apply false
    kotlin("android") version "1.7.20" apply false
    kotlin("multiplatform") version "1.7.20" apply false
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.1" apply false
    id("org.jetbrains.compose") version "1.2.2" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
