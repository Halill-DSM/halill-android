plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Project.compileSdk

    defaultConfig {
        applicationId = "com.halill.halill2"
        minSdk = Project.minSdk
        targetSdk = Project.targetSdk
        versionCode = 11
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
    composeOptions {
        kotlinCompilerExtensionVersion = Version.jetpackCompose
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependency.coreKtx)
    implementation(Dependency.appcompat)
    implementation(Dependency.javaInject)

    implementation(Dependency.UI.material)
    implementation(Dependency.UI.compose)
    implementation(Dependency.UI.composeMaterial)
    implementation(Dependency.UI.composePreview)
    implementation(Dependency.UI.composeActivity)
    implementation(Dependency.UI.composeLiveData)
    implementation(Dependency.UI.composeConstraintLayout)
    implementation(Dependency.UI.composeNavigation)
    implementation(Dependency.UI.composeMaterialIcon)
    implementation(Dependency.UI.composeNumberPicker)
    debugImplementation(Dependency.UI.composeTooling)

    implementation(Dependency.UI.accompanistPager)
    implementation(Dependency.UI.accompanistIconController)

    implementation(Dependency.DI.hilt)
    kapt(Dependency.DI.hiltCompiler)
    implementation(Dependency.DI.hiltViewModel)
    implementation(Dependency.DI.hiltViewModelNavigation)

    implementation(Dependency.Local.room)
    kapt(Dependency.Local.roomCompiler)

    implementation(platform(Dependency.FireBase.bom))
    implementation(Dependency.FireBase.auth)
    implementation(Dependency.FireBase.fireStore)

    implementation(Dependency.ThreeTenAndroidBackport.threeTenAbp)
}