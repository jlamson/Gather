@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {

    namespace = "com.darkmoose117.gather"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.darkmoose117.gather"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=enable")
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":scryfall"))

    implementation(libs.material)
    implementation(libs.bundles.androidxBase)
    implementation(libs.bundles.lifecycle)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.compose.navigation)

    implementation(libs.bundles.paging)

    implementation(libs.hilt)
    kapt(libs.hilt.kapt.compiler)

    implementation(libs.bundles.network)
    implementation(libs.bundles.moshi)
    implementation(libs.bundles.coil)
    implementation(libs.timber)

    testImplementation(testLibs.bundles.all)
    androidTestImplementation(androidTestLibs.bundles.all)
}