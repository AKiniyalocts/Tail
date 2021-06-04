import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.akiniyalocts.tail"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        val cocktailDbApiKey: String = gradleLocalProperties(rootProject.rootDir).getProperty("cocktailDbApiKey", "1") // default to '1' as that is the "test api key" for cocktaildb

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "cocktailDbApiKey", cocktailDbApiKey)

        }
        debug {
            buildConfigField("String", "cocktailDbApiKey", cocktailDbApiKey)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

dependencies {

    implementation("com.google.android.material:material:1.3.0")

    val kotlin_version = rootProject.extra["kotlin_version"] as String
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")

    val compose_version = rootProject.extra["compose_version"] as String

    implementation ("androidx.compose.runtime:runtime:$compose_version")
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.foundation:foundation-layout:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.material:material-icons-extended:$compose_version")
    implementation ("androidx.compose.foundation:foundation:$compose_version")
    implementation ("androidx.compose.animation:animation:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling:$compose_version")
    implementation ("androidx.compose.runtime:runtime-livedata:$compose_version")

    implementation ("androidx.appcompat:appcompat:1.3.0")
    implementation ("androidx.activity:activity-ktx:1.2.3")
    implementation ("androidx.core:core-ktx:1.6.0-alpha02")
    implementation ("androidx.activity:activity-compose:1.3.0-alpha08")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha05")

    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha01")
    implementation ("com.google.accompanist:accompanist-coil:0.10.0")

    implementation ("com.google.dagger:hilt-android:2.35")
    kapt ("com.google.dagger:hilt-android-compiler:2.35")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-alpha02")

    val roomVersion = "2.3.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")

    val moshiVersion = "1.9.3"
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    implementation("com.squareup.okhttp3:logging-interceptor:4.2.1")


    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
}