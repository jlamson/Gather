@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Gather"

include(":app")
include(":scryfall")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {

            library("material", "com.google.android.material:material:1.7.0")

            library("androidx.core", "androidx.core:core-ktx:1.9.0")
            library("androidx.appcompat", "androidx.appcompat:appcompat:1.6.0")
            library("androidx.activityCompose", "androidx.activity:activity-compose:1.6.1")
            library("androidx.annotation", "androidx.annotation:annotation:1.5.0")
            bundle("androidxBase", listOf(
                "androidx.core",
                "androidx.appcompat",
                "androidx.activityCompose",
                "androidx.annotation",
            ))

            library("androidx.pagingRuntime", "androidx.paging:paging-runtime-ktx:3.1.1")
            library("androidx.pagingCompose", "androidx.paging:paging-compose:1.0.0-alpha17")
            bundle("paging", listOf(
                "androidx.pagingRuntime",
                "androidx.pagingCompose",
            ))

            version("lifecycle", "2.5.1")
            library("lifecycle.runtime", "androidx.lifecycle", "lifecycle-runtime-ktx").versionRef("lifecycle")
            library("lifecycle.livedata", "androidx.lifecycle", "lifecycle-livedata-ktx").versionRef("lifecycle")
            library("lifecycle.viewModel", "androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")
            bundle("lifecycle", listOf(
                "lifecycle.runtime",
                "lifecycle.livedata",
                "lifecycle.viewModel",
            ))

            library("compose.bom", "androidx.compose:compose-bom:2023.01.00")
            library("compose.ui", "androidx.compose.ui", "ui").withoutVersion()
            library("compose.ui-tooling", "androidx.compose.ui", "ui-tooling").withoutVersion()
            library("compose.ui-util", "androidx.compose.ui", "ui-util").withoutVersion()
            library("compose.ui-viewbinding", "androidx.compose.ui", "ui-viewbinding").withoutVersion()
            library("compose.material", "androidx.compose.material", "material").withoutVersion()
            library("compose.material-icons-extended", "androidx.compose.material", "material-icons-extended").withoutVersion()
            library("compose.foundation", "androidx.compose.foundation", "foundation").withoutVersion()
            library("compose.foundation-layout", "androidx.compose.foundation", "foundation-layout").withoutVersion()
            library("compose.runtime", "androidx.compose.runtime", "runtime").withoutVersion()
            library("compose.runtime-livedata", "androidx.compose.runtime", "runtime-livedata").withoutVersion()
            /**
             * Note that "compose.bom" is not included. It's a platform, so you'll need to decalare
             * that separately, see below
             *
             * implementation(platform(libs.compose.bom))
             * implementation(libs.bundles.compose)
             */
            bundle("compose", listOf(
                "compose.ui",
                "compose.ui-tooling",
                "compose.ui-util",
                "compose.ui-viewbinding",
                "compose.material",
                "compose.material-icons-extended",
                "compose.foundation",
                "compose.foundation-layout",
                "compose.runtime",
                "compose.runtime-livedata",
            ))

            library("hilt","com.google.dagger:hilt-android:2.42")
            library("hilt.kapt.compiler","com.google.dagger:hilt-compiler:2.42")

            library("hilt.navigationCompose", "androidx.hilt:hilt-navigation-compose:1.0.0")
            library("androidx.navigationCompose", "androidx.navigation:navigation-compose:2.5.3")
            bundle("compose.navigation", listOf(
                "hilt.navigationCompose",
                "androidx.navigationCompose",
            ))

            version("retrofit", "2.9.0")
            library("network.retrofit", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("network.okhttp", "com.squareup.okhttp3:okhttp:4.10.0")
            library("network.okhttpLoggingInterceptor", "com.squareup.okhttp3:logging-interceptor:4.9.1")
            bundle("network", listOf(
                "network.retrofit",
                "network.okhttp",
                "network.okhttpLoggingInterceptor",
            ))

            version("moshi", "1.14.0")
            library("moshi", "com.squareup.moshi", "moshi").versionRef("moshi")
            library("moshi.adapters", "com.squareup.moshi", "moshi-adapters").versionRef("moshi")
            library("moshi.kapt.codegen", "com.squareup.moshi", "moshi-kotlin-codegen").versionRef("moshi")
            library("moshi.retrofitConverter", "com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            library("moshi.kotlin", "com.squareup.moshi", "moshi-kotlin").versionRef("moshi")
            bundle("moshi", listOf(
                "moshi",
                "moshi.adapters",
                "moshi.retrofitConverter",
            ))

            library("timber", "com.jakewharton.timber:timber:5.0.1")

            version("coil", "2.2.2")
            library("coil.compose", "io.coil-kt", "coil-compose").versionRef("coil")
            library("coil.svg", "io.coil-kt", "coil-svg").versionRef("coil")
            bundle("coil", listOf(
                "coil.compose",
                "coil.svg",
            ))
        }

        create("testLibs") {
            library("junit", "junit:junit:4.13.2")
            bundle("all", listOf(
                "junit"
            ))
        }

        create("androidTestLibs") {
            library("androidExtJunit", "androidx.test.ext:junit:1.1.5")
            library("espresso.core", "androidx.test.espresso:espresso-core:3.5.1")
            bundle("all", listOf(
                "androidExtJunit",
                "espresso.core",
            ))
        }
    }
}
