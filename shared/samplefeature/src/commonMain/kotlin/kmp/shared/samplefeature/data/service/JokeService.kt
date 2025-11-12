package kmp.shared.samplefeature.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kmp.shared.base.domain.error.util.runCatchingCommonNetworkExceptions
import kmp.shared.base.domain.model.Result
import kmp.shared.samplefeature.data.model.JokeDto

internal class JokeService(private val client: HttpClient) {

    suspend fun getRandomJoke(): Result<JokeDto> = runCatchingCommonNetworkExceptions {
        client.get("/random_joke").body()
    }
}

