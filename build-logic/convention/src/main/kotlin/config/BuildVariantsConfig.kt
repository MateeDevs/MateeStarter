package config

import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import constants.ProjectConstants
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.extra

internal fun <T : BuildType> CommonExtension<*, T, *, *, *, *>.configureBuildVariants() {
    buildTypes {
        debug {
            splits.abi.isEnable = false
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    flavorDimensions += ProjectConstants.ApiVariant.dimensionName
    productFlavors {
        create(ProjectConstants.ApiVariant.alpha) {
            dimension = ProjectConstants.ApiVariant.dimensionName
        }

        create(ProjectConstants.ApiVariant.production) {
            dimension = ProjectConstants.ApiVariant.dimensionName
        }
    }
}
