plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"

//    id ("com.google.devtools.ksp") version "1.6.10-1.0.2"
    id("dagger.hilt.android.plugin")
//    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "teka.android.chamaaapp"
    compileSdk = 34



    defaultConfig {
        applicationId = "teka.android.chamaaapp"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")


    // system UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // date time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    // Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    val compose_version = "1.5.4"
    val nav_version = "2.7.5"
    // Icons
    implementation("androidx.compose.material:material-icons-extended:$compose_version")

    // Room
    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:2.6.0")

    // Compose dependencies
    implementation("com.google.accompanist:accompanist-flowlayout:0.25.1")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.5")
    // Compose ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit & kotlinx
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation ("org.jetbrains.kotlin:kotlin-serialization:1.9.20")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Navigation animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.26.5-rc")

    // Accompanist System UI controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Insets
    implementation("com.google.accompanist:accompanist-insets:0.17.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Paging Compose
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")

    // Swipe to refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")

    // Livedata
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")

    // Preferences DataStore (SharedPreferences like APIs)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Ratingbar
    implementation("com.github.a914-gowtham:compose-ratingbar:1.2.3")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Composable-Graphs
    implementation("com.github.jaikeerthick:Composable-Graphs:v1.2.3")

    //lottie animations
    implementation("com.airbnb.android:lottie-compose:4.0.0")


}