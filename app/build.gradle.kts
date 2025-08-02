import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt") // kapt is needed
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.hiltproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hiltproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    viewBinding {
        enable = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {
    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Hilt ---
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    // --- Retrofit + Gson ---
    implementation("com.squareup.retrofit2:retrofit:2.11.0") // Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.11.0") // Gson Converter

    // --- Coroutines ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // --- Lifecycle (MVVM: ViewModel + LiveData) ---
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")

    // (Optional) Lifecycle annotations for ViewModel/LiveData
    kapt("androidx.lifecycle:lifecycle-compiler:2.8.1")

    // Room components
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

// For Kotlin Coroutines support
    implementation ("androidx.room:room-ktx:2.6.1")

    //firebase
    implementation ("com.google.firebase:firebase-crashlytics:18.3.5")  // Firebase Crashlytics
    implementation ("com.google.firebase:firebase-analytics:21.2.0")
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation ("com.google.firebase:firebase-config-ktx")
    implementation ("com.google.firebase:firebase-messaging:23.4.1")
    implementation ("com.google.firebase:firebase-database:20.3.1")
    implementation ("com.google.firebase:firebase-firestore:24.10.1")

    // Navigation Component
   /* def nav_version = "2.7.7" // latest stable as of now*/

    implementation ("androidx.navigation:navigation-fragment-ktx:$2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:$2.7.7")


    // For dynamic feature modules (optional if you use them)
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:$2.7.7")

    // Optional: Testing Navigation
    androidTestImplementation ("androidx.navigation:navigation-testing:$2.7.7")

    implementation ("com.github.pedroSG94.rtmp-rtsp-stream-client-java:rtplibrary:2.1.9")

    //AI SDK Dependency
    implementation("com.google.ai.client.generativeai:generativeai:0.6.0")

   // implementation("com.google.generativeai:generativeai:0.3.0")




}
