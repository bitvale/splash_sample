plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.COMMON_CONFIG_PLUGIN)
}

dependencies {
    implementation(project(":injection"))

    implementation(libs.liveDataKtx)
    implementation(libs.viewModelKtx)
    implementation(libs.fragmentKtx)
    implementation(libs.material)

    /*--Dagger--*/
    implementation(libs.daggerCore)
    kapt(libs.daggerKapt)

    /*--Rx--*/
    implementation(libs.bundles.rx)

    /*--Timber--*/
    implementation(libs.timber)

    /*--Reflect--*/
    implementation(libs.reflect)
}
