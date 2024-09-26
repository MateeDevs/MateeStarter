package kmp.shared.auth.infrastructure.remote

import io.ktor.client.HttpClient
import kmp.shared.base.Result
import kmp.shared.base.infrastucture.remote.clearBearerTokens
import kmp.shared.base.util.extension.success

internal class AuthService(private val client: HttpClient) {

    suspend fun logout(): Result<Unit> {
        client.clearBearerTokens()
        return Unit.success()
    }
}
