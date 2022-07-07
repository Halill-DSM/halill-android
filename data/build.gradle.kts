plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk
        targetSdk = Project.targetSdk

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
    compileOptions {
        sourceCompatibility = Project.javaVersion
        targetCompatibility = Project.javaVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependency.javaInject)

    testImplementation(Dependency.Test.junit)
    testImplementation(Dependency.Test.mockito)
    testImplementation(Dependency.Test.mockitoKotlin)
    testImplementation(Dependency.Test.mockitoInline)

    implementation(Dependency.Local.dataStore)
    implementation(Dependency.Local.dataStorePreference)

    implementation(Dependency.Local.room)
    implementation(Dependency.Local.roomRuntime)
    kapt(Dependency.Local.roomCompiler)

    implementation(platform(Dependency.FireBase.bom))
    implementation(Dependency.FireBase.auth)
    implementation(Dependency.FireBase.fireStore)

    implementation(Dependency.ThreeTenAndroidBackport.threeTenAbp)
}