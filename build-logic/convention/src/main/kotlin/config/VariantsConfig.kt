package config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import constants.Application
import constants.ProjectConstants

internal fun BaseAppModuleExtension.configureApplicationVariants() {
    applicationVariants.all {
        if (flavorName != ProjectConstants.ApiVariant.production) {
            resValue(
                "string",
                "app_name",
                "[${flavorName.first().uppercase()}] ${Application.appName}",
            )
        } else {
            resValue("string", "app_name", Application.appName)
        }
    }

    applicationVariants.firstOrNull()?.run {
        println("VersionName: $versionName | VersionCode: $versionCode")
    }
}
