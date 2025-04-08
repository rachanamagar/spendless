plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
    id ("kotlinx-serialization")
}

android {
    namespace = "com.myapp.spendless"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.myapp.spendless"
        minSdk = 29
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
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.core.splashscreen)

    //RoomDatabase
    implementation(libs.androidx.room)
    implementation(libs.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.retrofit.gson.convertor)

    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.datastore.preferences)

    androidTestImplementation (libs.androidx.junit.v115)
    androidTestImplementation (libs.androidx.espresso.core.v351)
    androidTestImplementation (libs.androidx.espresso.contrib)
    androidTestImplementation (libs.ui.test.junit4)
    debugImplementation (libs.ui.test.manifest)


    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // AndroidX Test Libraries
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.androidx.junit)

    testImplementation(libs.mockk)

    // Coroutines testing
    testImplementation(libs.kotlinx.coroutines.test)

    // Turbine for Flow testing
    testImplementation(libs.turbine)

    // Mockito for additional mocking support
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    // AndroidX Test Runner
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.core.testing)

}