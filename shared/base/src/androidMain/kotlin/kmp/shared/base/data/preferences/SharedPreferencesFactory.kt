package kmp.shared.base.data.preferences

import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings

class SharedPreferencesFactory(
    private val context: Context,
) {

    fun create(fileName: String, type: SharedPreferencesType): SharedPreferencesSettings {
        return SharedPreferencesSettings(
            when (type) {
                SharedPreferencesType.PLAIN -> {
                    context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                }

                SharedPreferencesType.ENCRYPTED -> {
                    SecureSharedPreferences(context = context, fileName = fileName)
                }
            },
        )
    }
}
