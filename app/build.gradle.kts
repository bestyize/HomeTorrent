import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.api.dsl.SigningConfig
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.kotlin.parcelize)
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

    signingConfigs {
        create("config") {
            keyAlias = properties["signKeyAlias"] as String
            keyPassword = properties["signKeyPassword"] as String
            storeFile = file("$rootDir/hyper_torrent.jks")
            storePassword = properties["signStorePassword"] as String
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs["config"]
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            )
        }
        named("debug") {
            signingConfig = signingConfigs["config"]
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTargetVersion.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTargetVersion.get().toInt())
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTargetVersion.get()
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=" +
                    "${project.rootDir}/compose_compiler_config.conf"
        )
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
    implementation(libs.composeNavigation)
    implementation(libs.gson)
    implementation(libs.mmkv)
    implementation(libs.torrent)
    implementation(libs.coli)
    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    implementation(libs.dataStore)
    implementation(libs.hilt)
    ksp(libs.hiltCompiler)
    implementation(project(mapOf("path" to ":framework:account")))
    implementation(project(mapOf("path" to ":framework:baseapp")))
    implementation(project(mapOf("path" to ":framework:network")))
    implementation(project(mapOf("path" to ":framework:downloader")))
    implementation(project(mapOf("path" to ":framework:resources")))
    implementation(project(mapOf("path" to ":framework:widget")))
    implementation(project(mapOf("path" to ":framework:utils")))
    implementation(libs.matrial)
    annotationProcessor(libs.roomCompiler)
    ksp(libs.roomCompiler)
    implementation(project(mapOf("path" to ":torrent")))
    implementation(project(mapOf("path" to ":community")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}