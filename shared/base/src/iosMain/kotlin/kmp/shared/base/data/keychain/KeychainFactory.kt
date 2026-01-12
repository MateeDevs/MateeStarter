package kmp.shared.base.data.keychain

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.CFBridgingRetain
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlock

class KeychainFactory {

    @OptIn(
        ExperimentalSettingsImplementation::class,
        ExperimentalForeignApi::class,
        ExperimentalSettingsApi::class,
    )
    fun create(): Settings {
        // There is a crash happening in current version that will be fixed in multiplatform-settings 1.4
        // this property should fix it, but if Keychain error still happens, please, use KeychainAccessibleAfterFirstUnlockSettings()
        // https://github.com/russhwolf/multiplatform-settings/issues/171
        return KeychainSettings(
            kSecAttrAccessible to CFBridgingRetain(kSecAttrAccessibleAfterFirstUnlock),
        )
    }
}
