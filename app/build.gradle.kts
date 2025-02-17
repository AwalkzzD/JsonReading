plugins {
    id("kotlin-kapt")
    id("io.realm.kotlin")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.jsonreading"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jsonreading"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    dataBinding { enable = true }
    viewBinding { enable = true }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.webkit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation("io.realm.kotlin:library-sync:1.11.0")

    /*    // jackson
        // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
        implementation("com.fasterxml.jackson.core:jackson-core:2.16.1")

        // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
        implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")

        // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations
        implementation("com.fasterxml.jackson.core:jackson-annotations:2.16.1")

        // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")*/

    // gson google
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation(libs.gson)

    // Kotlin Biometric Library
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

}