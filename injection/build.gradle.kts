plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.COMMON_CONFIG_PLUGIN)
}

dependencies {
    implementation(libs.fragmentKtx)
    implementation(libs.viewModelKtx)
    implementation(libs.material)

    /*--Dagger--*/
    implementation(libs.daggerCore)
    kapt(libs.daggerKapt)
}
