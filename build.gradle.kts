// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {

        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10") // Asegúrate de que esta línea esté presente
        classpath ("org.jetbrains.kotlin:kotlin-allopen:1.7.10")
    }
}
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

}


