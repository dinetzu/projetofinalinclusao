plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.projetofinalapril"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projetofinalapril"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation(libs.firebase.common)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    //implementação do room
    val room_version = "2.7.0"


    //implementação do firebase
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))


    implementation(libs.androidx.room.runtime)
    //processador de código
    annotationProcessor(libs.androidx.room.compiler)
    //suporte coroutines
    implementation(libs.androidx.room.rxjava2)
    implementation(libs.room.rxjava3)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}