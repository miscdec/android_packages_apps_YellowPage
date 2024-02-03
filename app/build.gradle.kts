plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("de.jensklingenberg.ktorfit") version "1.12.0"
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
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

    // define a BOM and its version
    implementation(platform(libs.okhttp.bom))
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation(libs.gson)

    api(libs.splitties.appctx)

    api(libs.libphonenumber)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}
