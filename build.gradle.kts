plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application") version "8.0.0-beta01" apply false
    id("com.android.library") version "8.0.0-beta01" apply false
    kotlin("android") version "1.8.0" apply false
    kotlin("multiplatform") version "1.8.0" apply false
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.3" apply false
    id("org.jetbrains.compose") version "1.3.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
