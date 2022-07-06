object Dependency {

    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    const val javaInject = "javax.inject:javax.inject:${Version.javaInject}"

    object UI {
        const val material = "com.google.android.material:material:${Version.material}"
        const val compose = "androidx.compose.ui:ui:${Version.jetpackCompose}"
        const val composeMaterial = "androidx.compose.material:material:${Version.jetpackCompose}"
        const val composePreview =
            "androidx.compose.ui:ui-tooling-preview:${Version.jetpackCompose}"
        const val composeActivity =
            "androidx.activity:activity-compose:${Version.activityCompose}"
        const val composeTooling = "androidx.compose.ui:ui-tooling:${Version.jetpackCompose}"
        const val composeLiveData =
            "androidx.compose.runtime:runtime-livedata:${Version.jetpackCompose}"
        const val composeConstraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:${Version.composeConstraintLayout}"
        const val composeNavigation =
            "androidx.navigation:navigation-compose:${Version.composeNavigation}"
        const val composeMaterialIcon =
            "androidx.compose.material:material-icons-extended:${Version.jetpackCompose}"
        const val composeNumberPicker =
            "com.chargemap.compose:numberpicker:${Version.composeNumberPicker}"
        const val accompanistPager =
            "com.google.accompanist:accompanist-pager:${Version.accompanistPager}"
        const val accompanistIconController =
            "com.google.accompanist:accompanist-systemuicontroller:${Version.accompanistIconController}"
    }

    object DI {
        const val hilt = "com.google.dagger:hilt-android:${Version.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
        const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hiltViewModel}"
        const val hiltViewModelNavigation =
            "androidx.hilt:hilt-navigation-compose:${Version.hiltViewModelNavigation}"
    }

    object Local {
        const val room = "androidx.room:room-ktx:${Version.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Version.room}"

        const val dataStore = "androidx.datastore:datastore:${Version.dataStore}"
        const val dataStorePreference = "androidx.datastore:datastore-preferences:${Version.dataStore}"
    }

    object Coroutine {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutineAndroid}"
    }

    object FireBase{
        const val bom = "com.google.firebase:firebase-bom:${Version.firebase}"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val fireStore = "com.google.firebase:firebase-firestore-ktx"
    }

    object Test {
        const val junit = "junit:junit:${Version.junit}"
        const val mockito = "org.mockito:mockito-core:${Version.mockito}"
        const val mockitoKotlin =
            "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.mockitoKotlin}"
        const val mockitoInline = "org.mockito:mockito-inline:${Version.mockitoInline}"
    }

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${Version.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
        const val gmsService = "com.google.gms:google-services:${Version.service}"
    }
}