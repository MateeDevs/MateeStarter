package kmp.shared.base.infrastucture.provider

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

internal class AuthProviderImpl(private val settings: Settings) : AuthProvider {

    override var token: String?
        get() = settings.getStringOrNull(TOKEN_KEY)
        set(value) = settings.set(TOKEN_KEY, value)

    private companion object {
        const val TOKEN_KEY = "token"
    }
}