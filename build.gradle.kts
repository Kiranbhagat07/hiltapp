plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false   // <-- Use alias
}
buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

    }
}

