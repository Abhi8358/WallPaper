import com.android.ddmlib.Log
import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.secrets)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.vedic.deepinsea"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vedic.deepinsea"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val secretsPropertiesFile = rootProject.file("secret.properties")
    val secretsProperties = Properties()
    secretsProperties.load(FileInputStream(secretsPropertiesFile))

    signingConfigs {
        create("release") {
            val keyStorePropertiesFile = rootProject.file("keystore.properties")
            val keyStoreProperties = Properties()
            keyStoreProperties.load(FileInputStream(keyStorePropertiesFile))

            keyAlias = keyStoreProperties["releaseKeyAlias"].toString()
            keyPassword = keyStoreProperties["releaseKeyPassword"].toString()
            //storeFile = keyStoreProperties["releaseKeyStore"]?.let { file(it) }
            val filePath = File(rootDir.canonicalPath + "/" + keyStoreProperties["releaseKeyStore"])
            storeFile=filePath

            storePassword = keyStoreProperties["releaseStorePassword"].toString()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "GEMINI_API_KEY", secretsProperties["GEMINI_API_KEY"].toString())
            buildConfigField("String", "PEXELS_BASE_URL", secretsProperties["PEXELS_BASE_URL"].toString())
            buildConfigField("String", "PEXELS_API_KEY", secretsProperties["PEXELS_API_KEY"].toString())
            buildConfigField("String", "PEXELS_API_KEY_2", secretsProperties["PEXELS_API_KEY_2"].toString())
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        debug {
            buildConfigField("String", "GEMINI_API_KEY", secretsProperties["GEMINI_API_KEY"].toString())
            buildConfigField("String", "PEXELS_BASE_URL", secretsProperties["PEXELS_BASE_URL"].toString())
            buildConfigField("String", "PEXELS_API_KEY", secretsProperties["PEXELS_API_KEY"].toString())
            buildConfigField("String", "PEXELS_API_KEY_2", secretsProperties["PEXELS_API_KEY_2"].toString())
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

secrets {
    defaultPropertiesFileName = "secret.properties"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.generative.ai)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3.window.size)

    kapt(libs.kotlin.kapt)

    implementation(libs.retrofit)
    implementation(libs.gson.convertor)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.convertor)
    implementation(libs.moshi)
    implementation(libs.okhttp)
    implementation(libs.constraint.layout.compose)
    implementation(libs.system.ui.controller)

    //coil
    implementation(libs.coil)

    // exoplayer
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.exoplayer)

    //splash screen api
    implementation(libs.splash.screen.api)
   // implementation(libs.play.services.ads)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
