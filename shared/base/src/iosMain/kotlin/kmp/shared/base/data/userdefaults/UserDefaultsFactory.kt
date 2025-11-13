package kmp.shared.base.data.userdefaults

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.NSUserDefaultsSettings

class UserDefaultsFactory {

    /**
     * @param name specifies the name of the NSUserDefaultsSettings, if name is `null` default NSUserDefaultsSettings are returned
     */
    @OptIn(ExperimentalSettingsImplementation::class)
    fun create(name: String? = null): NSUserDefaultsSettings {
        return NSUserDefaultsSettings.Factory().create(name)
    }
}
