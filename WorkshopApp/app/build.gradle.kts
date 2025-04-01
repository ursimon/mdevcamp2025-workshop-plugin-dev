plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.workshop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.workshop"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        missingDimensionStrategy("brand", "bravo")
        missingDimensionStrategy("environment", "production")
    }

    flavorDimensions += "brand"
    flavorDimensions += "environment"
    flavorDimensions += "territory"
    flavorDimensions += "store"

    productFlavors {
        create("alpha") {
            dimension = "brand"
            applicationIdSuffix = ".alpha"
            versionNameSuffix = "-alpha"
        }
        create("bravo") {
            dimension = "brand"
            applicationIdSuffix = ".bravo"
            versionNameSuffix = "-bravo"
            isDefault = true
        }
        create("charlie") {
            dimension = "brand"
            applicationIdSuffix = ".charlie"
            versionNameSuffix = "-charlie"
        }
        create("delta") {
            dimension = "brand"
            applicationIdSuffix = ".delta"
            versionNameSuffix = "-delta"
        }
        create("production") {
            dimension = "environment"
            applicationIdSuffix = ".prod"
            isDefault = true
        }
        create("development") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
        }
        create("secret") {
            dimension = "environment"
            applicationIdSuffix = ".sec"
        }
        create("google") {
            dimension = "store"
            applicationIdSuffix = ".google"
        }
        create("huawei") {
            dimension = "store"
            applicationIdSuffix = ".huawei"
        }
        create("amazon") {
            dimension = "store"
            applicationIdSuffix = ".amazon"
        }
        create("samsung") {
            dimension = "store"
            applicationIdSuffix = ".samsung"
        }
        create("cz") {
            dimension = "territory"
            applicationIdSuffix = ".cz"
        }
        create("sk") {
            dimension = "territory"
            applicationIdSuffix = ".sk"
        }
        create("de") {
            dimension = "territory"
            applicationIdSuffix = ".de"
        }
        create("pl") {
            dimension = "territory"
            applicationIdSuffix = ".pl"
        }
    }

    buildTypes {
        debug {
            matchingFallbacks += listOf("bravoProduction")
            isDefault = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            matchingFallbacks += listOf("bravoProduction")
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
    implementation(libs.androidx.navigation.compose)
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
}