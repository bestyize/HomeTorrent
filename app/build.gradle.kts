plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.home.torrent"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "com.home.torrent"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTargetVersion.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.coreKtx)
    implementation(platform(libs.kotlinBom))
    implementation(libs.lifecycleRuntimeKtx)
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.lifecycleViewModelCompose)
    implementation(libs.lifecycleRuntimeCompose)
    implementation(libs.appcompat)
    implementation(libs.composeConstraintLayout)
    implementation(libs.activityKtx)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.composeMaterial)
    implementation(libs.composeFoundation)
    implementation(libs.gson)
    implementation(project(mapOf("path" to ":torrent")))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.06.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(libs.composeUiTooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}