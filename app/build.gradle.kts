plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("jacoco")
}



android {
    namespace = "com.mrrsoftware.githublist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mrrsoftware.githublist"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    buildTypes {
        release {
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }


}

jacoco {
    toolVersion = "0.8.7"
}


tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*"
    )

    val debugTree = fileTree(mapOf(
        "dir" to "$buildDir/tmp/kotlin-classes/debug",
        "excludes" to fileFilter
    ))

    val mainSrc = "$projectDir/src/main/java"

    sourceDirectories.setFrom(files(listOf(mainSrc)))
    classDirectories.setFrom(files(listOf(debugTree)))
    executionData.setFrom(files(listOf(
        "$buildDir/jacoco/testDebugUnitTest.exec"
    )))
}

tasks.withType(Test::class) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.core)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito)

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.21")
    testImplementation("io.insert-koin:koin-test:3.1.2")

    // Mockito
    testImplementation("org.mockito:mockito-inline:3.11.2")
    testImplementation("org.mockito:mockito-android:3.11.2")

    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")

    // AndroidX Test
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // LiveData Testing
    testImplementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.4.0")

    // MockK
    testImplementation("io.mockk:mockk:1.12.0")

    testImplementation("org.robolectric:robolectric:4.7.3")

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //Glide
    implementation(libs.glide)
    kapt(libs.glideCompiler)


}