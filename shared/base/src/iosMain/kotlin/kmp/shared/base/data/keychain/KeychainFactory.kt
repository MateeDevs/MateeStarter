package kmp.shared.base.data.keychain

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.Settings

class KeychainFactory {

    @OptIn(ExperimentalSettingsImplementation::class)
    fun create(): Settings {
        // Will be fixed in multiplatform-settings 1.4
        // use KeychainSettings() when fixed
        // https://github.com/russhwolf/multiplatform-settings/issues/171
        return KeychainAccessibleAfterFirstUnlockSettings()
    }
}
