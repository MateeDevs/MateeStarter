package kmp.shared.base.system

import kmp.shared.base.BuildConfig

internal class ConfigImpl : Config {
    override val isRelease: Boolean
        get() = BuildConfig.BUILD_TYPE == "release"

    override val apiVariant: ApiVariant
        get() = ApiVariant.entries.firstOrNull { it.name.lowercase() == BuildConfig.FLAVOR } ?: ApiVariant.Develop
}
