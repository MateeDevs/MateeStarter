package kmp.shared.auth.data.provider

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kmp.shared.auth.data.remote.TokenRefresher
import kmp.shared.base.data.provider.AuthProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class AuthProviderImpl(
    private val settings: Settings,
    private val tokenRefresher: TokenRefresher,
) : AuthProvider {

    override var token: String?
        get() = settings.getStringOrNull(TOKEN_KEY)
        set(value) = settings.set(TOKEN_KEY, value)

    private val mutex = Mutex()
    private var inFlight: Deferred<String?>? = null

    override suspend fun refreshToken(): String? = coroutineScope {
        mutex.withLock {
            inFlight?.let { return@coroutineScope it.await() }

            val task = async {
                val fresh = tokenRefresher.refresh()
                token = fresh
                fresh
            }
            inFlight = task
            task
        }.let { task ->
            try {
                task.await()
            } finally {
                mutex.withLock {
                    if (inFlight === task) {
                        inFlight = null
                    }
                }
            }
        }
    }

    private companion object {
        const val TOKEN_KEY = "token"
    }
}
