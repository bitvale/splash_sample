import java.io.FileInputStream
import java.util.Properties

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("common-config-plugin") {
            id = "common-config-plugin"
            implementationClass = "plugin.CommonConfigPlugin"
        }
    }
}

repositories {
    mavenCentral()
    google()
}

kotlin {
    kotlinDslPluginOptions {
        val properties = getProjectProperties()
        val javaVersion: String by properties
        jvmTarget.set(javaVersion)
    }
}

java {
    val properties = getProjectProperties()
    val javaVersion: Int by properties
    val javaVersionValue = JavaVersion.toVersion(javaVersion)
    sourceCompatibility = javaVersionValue
    targetCompatibility = javaVersionValue
}

dependencies {
    val properties = getProjectProperties()

    val agpVersion: String by properties
    val kotlinVersion: String by properties

    implementation("com.android.tools.build:gradle:$agpVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

fun getProjectProperties(): Properties {
    val propsFile = project.file("../gradle.properties")
    val properties = Properties()
    properties.load(FileInputStream(propsFile))
    return properties
}