package kmp.shared.samplefeature.data.source.impl

import kmp.shared.base.domain.model.Result
import kmp.shared.samplefeature.data.model.JokeDto
import kmp.shared.samplefeature.data.source.JokeSource
import kmp.shared.samplefeature.infrastructure.service.JokeService

internal class JokeSourceImpl(private val service: JokeService) : JokeSource {

    override suspend fun getRandomJoke(): Result<JokeDto> =
        service.getRandomJoke()
}
