package plugin

import ProductFlavor as ProjectProductFlavor
import AndroidConfig
import BuildTypeConfig
import BuildTypeDebug
import BuildTypeRelease
import DebugConfig
import GradlePluginId
import ProductFlavorDevelop
import ProductFlavorProd
import ReleaseConfig
import SigningConfigs
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.ProductFlavor
import com.android.build.gradle.internal.dsl.SigningConfig
import java.io.File
import java.io.FileInputStream
import java.util.Properties
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidExtension = project.extensions.getByName("android")
        val androidComponentExtension = project.extensions
            .getByType(AndroidComponentsExtension::class.java)

        val properties = getProjectProperties(project)

        (androidExtension as? BaseExtension)?.apply {
            val pluginManager = project.pluginManager
            val isAppModule = pluginManager.hasPlugin(GradlePluginId.ANDROID_APPLICATION)
            compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

            defaultConfig {
                if (isAppModule) {
                    applicationId = AndroidConfig.ID
                } else {
                    consumerProguardFile(AndroidConfig.CONSUMER_PROGUARD_FILE)
                }
                targetSdk = AndroidConfig.TARGET_SDK_VERSION
                minSdk = AndroidConfig.MIN_SDK_VERSION

                versionCode = AndroidConfig.VERSION_CODE
                versionName = AndroidConfig.VERSION_NAME

                testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
            }

            signingConfigs {
                create(SigningConfigs.RELEASE) {
                    loadSigningConfigs(project, ReleaseConfig)
                }
                getByName(SigningConfigs.DEBUG) {
                    loadSigningConfigs(project, DebugConfig)
                }
            }

            buildTypes {
                getByName(BuildTypeConfig.RELEASE) {
                    configureBuildType(BuildTypeRelease, signingConfigs, androidExtension)
                }

                getByName(BuildTypeConfig.DEBUG) {
                    configureBuildType(BuildTypeDebug, signingConfigs, androidExtension)
                }

                create(BuildTypeConfig.LEAK) {
                    initWith(getByName(BuildTypeConfig.DEBUG))
                }
            }

            flavorDimensions(ProjectProductFlavor.ENVIRONMENT)

            productFlavors {
                create(ProductFlavorProd.name) {
                    dimension = ProjectProductFlavor.ENVIRONMENT
                    buildConfigFields(ProductFlavorProd)
                }

                create(ProductFlavorDevelop.name) {
                    dimension = ProjectProductFlavor.ENVIRONMENT
                    if (isAppModule) applicationIdSuffix = ProductFlavorDevelop.applicationIdSuffix
                    buildConfigFields(ProductFlavorDevelop)
                }
            }

            compileOptions {
                val javaVersion: Int by properties
                val javaVersionValue = JavaVersion.toVersion(javaVersion)
                targetCompatibility = javaVersionValue
                sourceCompatibility = javaVersionValue
            }

            androidComponentExtension.beforeVariants {
                if (it.buildType == BuildTypeConfig.LEAK) {
                    val isEnable = it.name.contains(ProjectProductFlavor.DEVELOP)
                    it.enable = isEnable
                    if (isEnable) {
                        sourceSets.getByName(BuildTypeConfig.LEAK) {
                            java.srcDir("src/debug/java")
                            resources.srcDir("src/debug/res")
                        }
                    }
                }
            }

            buildFeatures.viewBinding = true
        }

        (project.extensions.getByName("kotlin") as? KotlinProjectExtension)?.apply {
            sourceSets.all {
                languageSettings.optIn("kotlin.ExperimentalStdlibApi")
            }
        }

        project.tasks.withType(KotlinCompile::class.java) {
            val javaVersion: String by properties
            kotlinOptions.jvmTarget = javaVersion
        }
    }

    private fun SigningConfig.loadSigningConfigs(project: Project, configProps: SigningConfigs) {
        val rootPath = project.rootDir.absolutePath
        val keystorePropertiesFile = File(rootPath + File.separator + configProps.storeFile)
        if (keystorePropertiesFile.exists()) {
            val releaseKeystoreProperties = Properties()
            releaseKeystoreProperties.load(FileInputStream(keystorePropertiesFile))
            val storeFilePath =
                releaseKeystoreProperties.getProperty(SigningConfigs.STORE_FILE_PROP)
            storeFile = File(rootPath + File.separator + storeFilePath)
            storePassword =
                releaseKeystoreProperties.getProperty(SigningConfigs.STORE_PASSWORD_PROP)
            keyAlias = releaseKeystoreProperties.getProperty(SigningConfigs.KEY_ALIAS_PROP)
            keyPassword = releaseKeystoreProperties.getProperty(SigningConfigs.KEY_PASSWORD_PROP)
        }
    }

    private fun BuildType.configureBuildType(
        config: BuildTypeConfig,
        signingConfigs: NamedDomainObjectContainer<SigningConfig>,
        baseExtension: BaseExtension
    ) {
        proguardFiles(
            baseExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
        isMinifyEnabled = config.isMinifyEnabled
        signingConfig = signingConfigs.getByName(config.signingConfigs)
    }

    private fun ProductFlavor.buildConfigFields(flavor: ProjectProductFlavor) {
        buildConfigField("String", ProjectProductFlavor::baseUrl.name, flavor.baseUrl)
        buildConfigField("String", ProjectProductFlavor::versionName.name, flavor.versionName)
        buildConfigField("int", "versionCode", AndroidConfig.VERSION_CODE.toString())
    }

    private fun getProjectProperties(project: Project): Properties {
        val rootPath = project.rootDir.absolutePath
        val propertiesFile = File(rootPath + File.separator + "gradle.properties")
        val properties = Properties()
        properties.load(FileInputStream(propertiesFile))
        return properties
    }
}
