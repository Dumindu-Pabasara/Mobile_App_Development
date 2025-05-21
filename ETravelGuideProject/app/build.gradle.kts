plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.etravelguideproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.etravelguideproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Access OPENAI_KEY from gradle.properties
        val openaiKey: String? = project.findProperty("OPENAI_KEY") as String?

        buildConfigField("String", "OPENAI_KEY", "\"${openaiKey ?: ""}\"")
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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // AndroidX and Material
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity:1.10.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    // Inside dependencies
    implementation("com.google.firebase:firebase-auth:22.3.1") // latest as of 2025


    // Glide (Image Loading)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Gson and Retrofit libs (make sure these are defined in your versions catalog or replace with versions)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //Ai
    implementation("com.squareup.okhttp3:okhttp:4.12.0")


    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
