plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.skindisease"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.skindisease"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation compose
    implementation(libs.androidx.navigation.compose)
    //    Koin
    implementation(libs.koin.compose)
    //    Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.serialization.gson)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.client.resource)
    implementation(libs.ktor.client.logging)
    // MaterialKolor
    implementation(libs.material.kolor)
    // Camera
    implementation(libs.androidx.camera.camera.camera)
    implementation(libs.androidx.camera.camera.view)
    implementation(libs.androidx.camera.camera.core)
    implementation(libs.androidx.camera.camera.lifecycle)
    // Material Icons extended
    implementation(libs.androidx.compose.material.material.icons.extended)
    // Coil Compose
    implementation(libs.io.coil.kt.coil3.coil.compose)
    // lottie compose
    implementation(libs.lottie.compose)
    //    Room
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)
    ksp(libs.androidx.room.room.compiler)
//    Compose Webview
    implementation(libs.compose.webview)
}