import java.io.FileInputStream
import java.util.Properties

// ======================================================
// СИСТЕМА ЗАГРУЗКИ СЕКРЕТОВ (secrets.properties)
// ======================================================
val secretsFile = rootProject.file("secrets.properties")
val secrets = Properties().apply {
    if (secretsFile.exists()) {
        load(FileInputStream(secretsFile))
    } else {
        println("⚠️  ВНИМАНИЕ: secrets.properties не найден. Создай его из secrets.properties.example")
    }
}

fun getSecret(key: String, default: String = ""): String {
    return secrets.getProperty(key, default)
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    kotlin("plugin.serialization")
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.example.classschedule"
    compileSdk = 36

    defaultConfig {
        multiDexEnabled = true
        applicationId = "com.example.classschedule"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // === Секреты из secrets.properties (Фаза 0) ===
        buildConfigField("String", "SUPABASE_URL", "\"${getSecret("SUPABASE_URL", "https://placeholder.supabase.co")}\"")
        buildConfigField("String", "SUPABASE_KEY", "\"${getSecret("SUPABASE_KEY", "placeholder_key")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true   // Нужно для BuildConfig.SUPABASE_URL и т.д.
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}



dependencies {
    // === Multiplatform Settings (нужно для сохранения сессии Supabase) ===
    implementation("com.russhwolf:multiplatform-settings:1.1.1")
    implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")

// === Supabase ===
    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.0"))
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:postgrest-kt")


// === Остальные зависимости  ===
    implementation("io.github.fletchmckee.liquid:liquid:1.0.0")
    implementation(libs.androidx.core.splashscreen)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.runtime)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.runtime.runtime)
    implementation(libs.androidx.credentials)
    ksp("androidx.room:room-compiler:2.6.1")

// Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

// Ktor (нужен Supabase)
    implementation(libs.ktor.client.android)

// Compose + AndroidX
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

// Тесты
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(kotlin("test"))
}