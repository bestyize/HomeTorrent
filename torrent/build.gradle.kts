plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.home.torrent"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
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
}

dependencies {

    implementation(libs.gson)
    implementation(libs.jsoup)
    implementation(libs.kotlinxCoroutinesCore)
    implementation(libs.mmkv)
    implementation(libs.torrent)
    implementation(libs.lifecycleRuntimeCompose)
    implementation(libs.lifecycleViewModelCompose)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.composeMaterial)
    implementation(libs.composeFoundation)
    implementation(libs.roomKtx)
    implementation(libs.roomRuntime)
    implementation(libs.kotlinImmutable)
    implementation(project(mapOf("path" to ":framework:network")))
    implementation(project(mapOf("path" to ":framework:baseapp")))
    implementation(project(mapOf("path" to ":framework:widget")))
    implementation(project(mapOf("path" to ":framework:utils")))
    implementation(project(mapOf("path" to ":framework:resources")))

    annotationProcessor(libs.roomCompiler)
    ksp(libs.roomCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}