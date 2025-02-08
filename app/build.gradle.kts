import org.apache.tools.ant.util.JavaEnvUtils.VERSION_1_8

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
/*    alias(libs.plugins.androidApplication)*/
    /*    alias(libs.plugins.kotlinAndroid)*/
    alias(libs.plugins.androidLibrary)
    // id("com.android.library")
    alias(libs.plugins.kotlinAndroid)
    id("maven-publish")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.XeeroSs"
                artifactId = "BetterUI-JetPackCompose"
                version = "1.0.0"
            }
        }
    }
}

android {
    namespace = "fr.xeross.betterui"
    compileSdk = 35

    defaultConfig {
        // applicationId = "fr.xeross.betterui"
        minSdk = 29
        testOptions.targetSdk = 35
        /*     versionCode = 1
             versionName = "1.0"*/

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17  // Utilise la version 17
        targetCompatibility = JavaVersion.VERSION_17  // Utilise la version 17
    }
    kotlinOptions {
        jvmTarget = "17"  // Assure-toi que Kotlin utilise aussi Java 17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}