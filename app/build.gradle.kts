

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.sebastianvm.mango"
    compileSdk = 33
    compileSdkPreview = "UpsideDownCakePrivacySandbox"

    defaultConfig {
        applicationId = "com.sebastianvm.mango"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ksp {
        // TODO uncomment once we have the first valid version of DB
        // arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.generateKotlin", "true")
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(libs.viewmodel.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.kotlin.serialization)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.kotlinx.datetime)

    implementation(project(":fakegen"))
    kspDebug(project(":fakegen"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.google.truth)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
