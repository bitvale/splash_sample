plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.SAFE_ARGS)
    id(GradlePluginId.GOOGLE_SERVICES) apply false
    id(GradlePluginId.CRASHLYTICS)
    id(GradlePluginId.COMMON_CONFIG_PLUGIN)
}

dependencies {
    implementation(project(":core:presentation"))
    implementation(project(":injection"))
    implementation(project(":resources"))
    implementation(project(":feature:initial"))

    implementation(libs.navigationUiKtx)
    implementation(libs.navigationFragmentKtx)
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.splash)


    /*--Dagger--*/
    implementation(libs.daggerCore)
    kapt(libs.daggerKapt)

    /*--Timber--*/
    implementation(libs.timber)
}

apply(plugin = GradlePluginId.GOOGLE_SERVICES)
