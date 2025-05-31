import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.example.bingebox"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bingebox"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("api_key", "")}\"")
        buildConfigField("String", "API_HOST", "\"${localProperties.getProperty("api_host", "")}\"")
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

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.retrofitConverterGson)
    implementation(libs.retrofit)
    implementation(libs.lifeCycle)
    implementation(libs.liveData)
    implementation(libs.roomruntime)
    annotationProcessor(libs.roomcompiler)
    implementation(libs.activity)
    implementation(libs.glide)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAnalytics)
}