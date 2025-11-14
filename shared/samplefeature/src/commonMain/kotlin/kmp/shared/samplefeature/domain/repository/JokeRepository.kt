package kmp.shared.samplefeature.domain.repository

import kmp.shared.base.domain.model.Result
import kmp.shared.samplefeature.domain.model.Joke

internal interface JokeRepository {
    suspend fun getRandomJoke(): Result<Joke>
}
