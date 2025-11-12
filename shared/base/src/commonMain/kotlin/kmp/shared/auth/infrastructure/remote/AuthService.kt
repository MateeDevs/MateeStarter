package kmp.shared.auth.infrastructure.remote

import io.ktor.client.HttpClient
import kmp.shared.base.domain.model.Result
import kmp.shared.base.domain.util.extension.success
import kmp.shared.base.infrastucture.remote.clearBearerTokens

internal class AuthService(private val client: HttpClient) {

    suspend fun logout(): Result<Unit> {
        client.clearBearerTokens()
        return Unit.success()
    }
}
