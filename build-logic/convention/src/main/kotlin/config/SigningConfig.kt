package config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import constants.ProjectConstants
import org.gradle.api.Project

internal fun Project.configureSingingConfigs(
    module: BaseAppModuleExtension,
) = with(module) {
    signingConfigs {
        named(ProjectConstants.BuildVariant.debug).configure {
            storeFile = file("../../other/keystore/debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        register(ProjectConstants.BuildVariant.release) {
            storeFile = file("../../other/keystore/release.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".${ProjectConstants.BuildVariant.debug}"
            signingConfig = signingConfigs.getByName(ProjectConstants.BuildVariant.debug)
        }

        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName(ProjectConstants.BuildVariant.release)
        }
    }

    productFlavors {
        all {
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
        }
    }
}
