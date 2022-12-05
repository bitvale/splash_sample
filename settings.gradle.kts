rootProject.buildFileName = "build.gradle.kts"

include(
    ":app",
    ":feature:initial",
    ":navigation:navigation",
    ":navigation:contract",
    ":core:presentation",
    ":injection",
    ":resources",
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    val agpVersion: String by settings
    val kotlinVersion: String by settings
    val navigationVersion: String by settings
    val googleServicesVersion: String by settings
    val crashlyticsVersion: String by settings
    val ktlintWrapperVersion: String by settings

    plugins {
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion

        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.android") version kotlinVersion

        id("com.google.gms.google-services") version googleServicesVersion
        id("com.google.firebase.crashlytics") version crashlyticsVersion

        id("androidx.navigation.safeargs.kotlin") version navigationVersion

        id("org.jlleitschuh.gradle.ktlint") version ktlintWrapperVersion
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library" -> {
                    useModule("com.android.tools.build:gradle:$agpVersion")
                }
                "androidx.navigation.safeargs" -> {
                    useModule(
                        "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
                    )
                }
                "com.google.gms.google-services" -> {
                    useModule("com.google.gms:google-services:$googleServicesVersion")
                }
                "com.google.firebase.crashlytics" -> {
                    useModule(
                        "com.google.firebase:firebase-crashlytics-gradle:$crashlyticsVersion"
                    )
                }
            }
        }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library(
                "reflect",
                "org.jetbrains.kotlin:kotlin-reflect:1.7.20"
            )

            val daggerVersion: String by settings
            version("dagger", daggerVersion)
            library("daggerCore", "com.google.dagger", "dagger")
                .versionRef("dagger")

            library(
                "daggerKapt",
                "com.google.dagger",
                "dagger-compiler"
            )
                .versionRef("dagger")

            val timberVersion: String by settings
            library(
                "timber",
                "com.jakewharton.timber:timber:$timberVersion"
            )

            val firebaseVersion: String by settings
            library(
                "firebaseBom",
                "com.google.firebase:firebase-bom:$firebaseVersion"
            )
            library(
                "firebaseMessaging",
                "com.google.firebase",
                "firebase-messaging-ktx"
            )
                .withoutVersion()
            library(
                "firebaseAnalytics",
                "com.google.firebase",
                "firebase-analytics-ktx"
            )
                .withoutVersion()
            library(
                "firebaseCrashlytics",
                "com.google.firebase",
                "firebase-crashlytics-ktx"
            )
                .withoutVersion()
            library(
                "firebaseDatabase",
                "com.google.firebase",
                "firebase-database-ktx"
            )
                .withoutVersion()
            bundle(
                "firebase",
                listOf(
                    "firebaseAnalytics",
                    "firebaseCrashlytics"
                )
            )

            val rxJavaVersion: String by settings
            version("rxjava3", rxJavaVersion)
            library(
                "rxjava3",
                "io.reactivex.rxjava3",
                "rxjava"
            )
                .versionRef("rxjava3")

            val rxAndroidVersion: String by settings
            library(
                "rxandroid",
                "io.reactivex.rxjava3:rxandroid:$rxAndroidVersion"
            )
            val rxKotlinVersion: String by settings
            library(
                "rxkotlin",
                "io.reactivex.rxjava3:rxkotlin:$rxKotlinVersion"
            )

            bundle("rx", listOf("rxjava3", "rxandroid", "rxkotlin"))

            library("gson", "com.google.code.gson:gson:2.9.0")

            val retrofitVersion: String by settings
            version("retrofit", retrofitVersion)
            library("retrofitCore", "com.squareup.retrofit2", "retrofit")
                .versionRef("retrofit")
            library(
                "retrofitGsonConverter",
                "com.squareup.retrofit2",
                "converter-gson"
            )
                .versionRef("retrofit")
            library(
                "adapterRxjava3",
                "com.squareup.retrofit2",
                "adapter-rxjava3"
            )
                .versionRef("retrofit")
            val okhttpVersion: String by settings
            library("okhttp", "com.squareup.okhttp3:okhttp:$okhttpVersion")
            bundle(
                "retrofit",
                listOf("retrofitCore", "adapterRxjava3", "retrofitGsonConverter", "okhttp")
            )

            val flipperVersion: String by settings
            version("flipper", flipperVersion)
            library(
                "flipperCore",
                "com.facebook.flipper",
                "flipper"
            )
                .versionRef("flipper")
            library(
                "flipperNetworkPlugin",
                "com.facebook.flipper",
                "flipper-network-plugin"
            )
                .versionRef("flipper")
            library(
                "flipperNoop",
                "com.facebook.flipper",
                "flipper-noop"
            )
                .versionRef("flipper")
            val soloaderVersion: String by settings
            library(
                "soloader",
                "com.facebook.soloader:soloader:$soloaderVersion"
            )
            bundle(
                "flipper",
                listOf("flipperCore", "flipperNetworkPlugin", "soloader")
            )

            val leakcanaryVersion: String by settings
            library(
                "leakcanary",
                "com.squareup.leakcanary:leakcanary-android:$leakcanaryVersion"
            )

            val roomVersion: String by settings
            version("room", roomVersion)
            library("roomCompiler", "androidx.room", "room-compiler")
                .versionRef("room")
            library("roomRuntime", "androidx.room", "room-runtime")
                .versionRef("room")
            library("roomRxjava3", "androidx.room", "room-rxjava3")
                .versionRef("room")

            val preferenceKtxVersion: String by settings
            library(
                "preferenceKtx",
                "androidx.preference:preference-ktx:$preferenceKtxVersion"
            )
            val coreKtxVersion: String by settings
            library(
                "coreKtx",
                "androidx.core:core-ktx:$coreKtxVersion"
            )
            val appcompatVersion: String by settings
            library(
                "appcompat",
                "androidx.appcompat:appcompat:$appcompatVersion"
            )
            val materialVersion: String by settings
            library(
                "material",
                "com.google.android.material:material:$materialVersion"
            )

            val fragmentVersion: String by settings
            version("fragment", fragmentVersion)
            library(
                "fragmentKtx",
                "androidx.fragment",
                "fragment-ktx"
            )
                .versionRef("fragment")

            val splashVersion: String by settings
            library(
                "splash",
                "androidx.core:core-splashscreen:$splashVersion"
            )

            val lifecycleVersion: String by settings
            version("lifecycle", lifecycleVersion)

            library(
                "viewModelKtx",
                "androidx.lifecycle",
                "lifecycle-viewmodel-ktx"
            )
                .versionRef("lifecycle")
            library(
                "liveDataKtx",
                "androidx.lifecycle",
                "lifecycle-livedata-ktx"
            )
                .versionRef("lifecycle")
            library(
                "lifecycleCommon",
                "androidx.lifecycle",
                "lifecycle-common-java8"
            )
                .versionRef("lifecycle")

            val navigationVersion: String by settings
            version("navigation", navigationVersion)
            library(
                "navigationFragmentKtx",
                "androidx.navigation",
                "navigation-fragment-ktx"
            )
                .versionRef("navigation")
            library(
                "navigationUiKtx",
                "androidx.navigation",
                "navigation-ui-ktx"
            )
                .versionRef("navigation")
            bundle(
                "navigation",
                listOf("navigationFragmentKtx", "navigationUiKtx")
            )

            val billingVersion: String by settings
            library(
                "billing",
                "com.android.billingclient:billing-ktx:$billingVersion"
            )

            val adsVersion: String by settings
            library(
                "ads",
                "com.google.android.gms:play-services-ads:$adsVersion"
            )
        }
    }
}
