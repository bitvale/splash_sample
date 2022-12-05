object AndroidConfig {
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 24
    const val TARGET_SDK_VERSION = 33

    const val VERSION_CODE = 58
    const val VERSION_NAME = "3.0.3"

    const val ID = "com.skycodetech.codingquiz"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val CONSUMER_PROGUARD_FILE = "consumer-rules.pro"
}

interface SigningConfigs {

    val storeFile: String

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"

        const val STORE_FILE_PROP = "storeFile"
        const val STORE_PASSWORD_PROP = "storePassword"
        const val KEY_ALIAS_PROP = "keyAlias"
        const val KEY_PASSWORD_PROP = "keyPassword"
    }
}

object ReleaseConfig : SigningConfigs {
    override val storeFile = "keystore/keystore_release.properties"
}

object DebugConfig : SigningConfigs {
    override val storeFile = "keystore/keystore_debug.properties"
}

interface BuildTypeConfig {

    val isMinifyEnabled: Boolean
    val signingConfigs: String

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
        const val LEAK = "leak"
    }
}

object BuildTypeDebug : BuildTypeConfig {
    override val isMinifyEnabled = false
    override val signingConfigs = SigningConfigs.DEBUG
}

object BuildTypeRelease : BuildTypeConfig {
    override val isMinifyEnabled = true
    override val signingConfigs = SigningConfigs.RELEASE
}

interface ProductFlavor {

    val name: String
    val versionName: String
    val applicationIdSuffix: String
    val baseUrl: String

    companion object {
        const val ENVIRONMENT = "environment"
        const val LEAK = "leak"

        const val PRODUCTION = "production"
        const val DEVELOP = "develop"
    }
}

object ProductFlavorDevelop : ProductFlavor {
    override val name = ProductFlavor.DEVELOP
    override val versionName = "\"${AndroidConfig.VERSION_NAME}-$name\""
    override val applicationIdSuffix = ProductFlavor.DEVELOP
    override val baseUrl = "\"http://192.168.1.44:8080/\""
}

object ProductFlavorProd : ProductFlavor {
    override val name = ProductFlavor.PRODUCTION
    override val versionName = "\"${AndroidConfig.VERSION_NAME}\""
    override val applicationIdSuffix = ""
    override val baseUrl = "\"http://173.199.124.49/api/\""
}
