plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("de.jensklingenberg.ktorfit") version "1.12.0"
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
    kotlin("plugin.serialization").version("1.9.21")
}

android {
    namespace = "org.exthmui.yellowpage"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.exthmui.yellowpage"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
//    implementation(fileTree(dir: ("libs"), include: ['*.jar','*.aar']))

    implementation(project(":ExthmCollapsingToolbarBaseActivity"))
    implementation(project(":SettingsTheme"))

    implementation(libs.androidx.core.ktx)
    implementation(kotlin("stdlib-jdk8"))


    implementation(libs.ktorfit.lib)
    ksp(libs.ktorfit.ksp)

    // Ktor
    // https://mvnrepository.com/artifact/io.ktor/ktor-bom
    implementation(platform(libs.ktor.bom))
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-okhttp")
    implementation("io.ktor:ktor-client-android")
//    implementation("ch.qos.logback:logback-classic:+")
    implementation("io.ktor:ktor-client-logging")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
// https://mvnrepository.com/artifact/org.slf4j/slf4j-nop
    implementation(libs.slf4j.nop)

    // define a BOM and its version
    implementation(platform(libs.okhttp.bom))
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation(libs.gson)

    api(libs.splitties.appctx)

    api(libs.libphonenumber)

    // https://mvnrepository.com/artifact/commons-codec/commons-codec
    implementation(libs.commons.codec)



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit.jupiter)

}
