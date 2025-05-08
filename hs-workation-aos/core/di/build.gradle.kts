import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

android {
    namespace = "com.hs.workation.core.di"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "AUTH_URL", "${localProperties["AUTH_URL_DEV"]}")
            buildConfigField("String", "BASE_URL", "${localProperties["BASE_URL_DEV"]}")
            buildConfigField("String", "BASE_PATH", "${localProperties["BASE_PATH"]}")
        }
        release {
            buildConfigField("String", "AUTH_URL", "${localProperties["AUTH_URL"]}")
            buildConfigField("String", "BASE_URL", "${localProperties["BASE_URL"]}")
            buildConfigField("String", "BASE_PATH", "${localProperties["BASE_PATH"]}")

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
        buildConfig = true
    }
}

dependencies {
    /* Module */
    implementation(project(":core:common"))
    implementation(project(":core:resource"))
    implementation(project(":core:util"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    /* Hilt */
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    /* Retrofit */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)

    /* Okhttp Interceptor */
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}