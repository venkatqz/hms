plugins {
    id("com.android.application") // ✅ Required for Android project
    id("org.jetbrains.kotlin.android") // ✅ Required for Kotlin in Android
    id("com.google.gms.google-services") // ✅ Firebase (if needed)
}

android {
    namespace = "com.example.hms"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hms"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
//    testOptions {
//        unitTests.isIncludeAndroidResources = true
//    }
}
