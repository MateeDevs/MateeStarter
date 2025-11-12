package kmp.shared.samplefeature.data.repository

import kmp.shared.base.domain.model.Result
import kmp.shared.base.domain.util.extension.map
import kmp.shared.samplefeature.data.model.JokeDto
import kmp.shared.samplefeature.data.model.toDomain
import kmp.shared.samplefeature.data.source.JokeSource
import kmp.shared.samplefeature.domain.model.Joke
import kmp.shared.samplefeature.domain.repository.JokeRepository

internal class JokeRepositoryImpl(private val source: JokeSource) : JokeRepository {

    override suspend fun getRandomJoke(): Result<Joke> =
        source.getRandomJoke()
            .map(JokeDto::toDomain)
}
