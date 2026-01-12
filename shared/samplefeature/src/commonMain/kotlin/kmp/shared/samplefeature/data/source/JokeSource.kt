package kmp.shared.samplefeature.data.source

import kmp.shared.base.domain.model.Result
import kmp.shared.samplefeature.data.model.JokeDto

internal interface JokeSource {
    suspend fun getRandomJoke(): Result<JokeDto>
}
