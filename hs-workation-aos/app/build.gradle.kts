plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hs.workation"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hs.workation"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            resValue("string", "app_name", "횡성워케이션(DEV)")
        }
        release {
            resValue("string", "app_name", "횡성워케이션")

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
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    /* Module */
    implementation(project(":core:base"))
    implementation(project(":core:common"))
    implementation(project(":core:component"))
    implementation(project(":core:di"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:util"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:home"))
    implementation(project(":feature:login"))
    implementation(project(":feature:main"))
    implementation(project(":feature:mobility"))
    implementation(project(":feature:my"))
    implementation(project(":feature:permission"))
    implementation(project(":feature:reservation"))
    implementation(project(":feature:space"))
    implementation(project(":feature:splash"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    /* Firebase */
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.messaging)

    /* Hilt */
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    
    /* Map */
    implementation(libs.map.sdk)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}