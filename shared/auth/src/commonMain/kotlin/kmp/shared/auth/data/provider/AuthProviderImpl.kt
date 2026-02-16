package kmp.shared.auth.data.provider

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kmp.shared.auth.data.remote.TokenRefresher
import kmp.shared.base.data.provider.AuthProvider
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class AuthProviderImpl(
    private val settings: Settings,
    private val tokenRefresher: TokenRefresher,
) : AuthProvider {

    override var token: String?
        get() = settings.getStringOrNull(TOKEN_KEY)
        set(value) = settings.set(TOKEN_KEY, value)

    private val mutex = Mutex()

    override suspend fun refreshToken(): String? {
        val oldToken = token

        mutex.withLock {
            if (oldToken == token) {
                withContext(NonCancellable) {
                    token = tokenRefresher.refresh()
                }
            }
            return token
        }
    }

    private companion object {
        const val TOKEN_KEY = "token"
    }
}
