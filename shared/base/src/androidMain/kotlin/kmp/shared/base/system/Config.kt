package kmp.shared.base.system

import kmp.shared.base.BuildConfig

actual class Config {
    actual val isRelease: Boolean
        get() = BuildConfig.BUILD_TYPE == "release"
}
