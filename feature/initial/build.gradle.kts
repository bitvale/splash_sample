plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_PARCELIZE)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.SAFE_ARGS)
    id(GradlePluginId.COMMON_CONFIG_PLUGIN)
}

dependencies {
    implementation(project(":core:presentation"))
    implementation(project(":injection"))
    implementation(project(":resources"))

    implementation(libs.material)

    /*--Dagger--*/
    implementation(libs.daggerCore)
    kapt(libs.daggerKapt)

    /*--Timber--*/
    implementation(libs.timber)
}
