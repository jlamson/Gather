plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.androidx.annotation)

    implementation(libs.bundles.network)
    implementation(libs.bundles.moshi)
    kapt(libs.moshi.kapt.codegen)

    testImplementation(testLibs.bundles.all)
    testImplementation(libs.moshi.kotlin)
}

