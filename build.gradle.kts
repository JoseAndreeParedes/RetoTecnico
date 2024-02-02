buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlin_version = "1.8.22"
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
    }
}

plugins {
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.android.application") version "8.1.3" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}