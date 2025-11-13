package kmp.shared.auth.data.remote

import io.ktor.client.HttpClient
import kmp.shared.base.data.remote.clearBearerTokens
import kmp.shared.base.domain.model.Result
import kmp.shared.base.domain.util.extension.success

internal class AuthService(private val client: HttpClient) {

    suspend fun logout(): Result<Unit> {
        client.clearBearerTokens()
        return Unit.success()
    }
}
