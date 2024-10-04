package config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import constants.Application
import constants.ProjectConstants

internal fun BaseAppModuleExtension.configureApplicationVariants() {
    applicationVariants.all {
        if (buildType.name != ProjectConstants.BuildVariant.release || flavorName != ProjectConstants.ApiVariant.production) {
            resValue(
                "string",
                "app_name",
                "[${flavorName.uppercase()}] [${buildType.name.uppercase()}] ${Application.appName}",
            )
        } else {
            resValue("string", "app_name", Application.appName)
        }
    }

    applicationVariants.firstOrNull()?.run {
        println("VersionName: $versionName | VersionCode: $versionCode")
    }
}
