

plugins {
    id(GradlePluginId.KTLINT_GRADLE)
    id(GradlePluginId.ANDROID_APPLICATION) apply false
    id(GradlePluginId.KOTLIN_ANDROID) apply false
    id(GradlePluginId.SAFE_ARGS) apply false
    id(GradlePluginId.CRASHLYTICS) apply false
    id(GradlePluginId.COMMON_CONFIG_PLUGIN) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    apply(plugin = GradlePluginId.KTLINT_GRADLE)

    ktlint {
        verbose.set(true)
        android.set(true)

        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }

        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
