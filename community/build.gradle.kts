@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.thewind.community"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTargetVersion.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTargetVersion.get().toInt())
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

}

dependencies {

    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.matrial)
    implementation(libs.lifecycleViewModelCompose)
    implementation(libs.lifecycleRuntimeCompose)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))

    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.composeMaterial)
    implementation(libs.composeFoundation)
    implementation(libs.composeNavigation)
    implementation(libs.gson)

    implementation(libs.coli)
    implementation(project(mapOf("path" to ":framework:account")))
    implementation(project(mapOf("path" to ":framework:baseapp")))
    implementation(project(mapOf("path" to ":framework:network")))
    implementation(project(mapOf("path" to ":framework:widget")))
    implementation(project(mapOf("path" to ":framework:utils")))
    implementation(project(mapOf("path" to ":framework:resources")))
    implementation(libs.constraintlayout)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.ui.test.manifest)

}