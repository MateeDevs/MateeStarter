package kmp.shared.samplefeature.domain.usecase

import kmp.shared.base.domain.model.Result
import kmp.shared.base.domain.usecase.UseCaseResultNoParams
import kmp.shared.samplefeature.domain.model.Joke
import kmp.shared.samplefeature.domain.repository.JokeRepository

interface GetRandomJokeUseCase : UseCaseResultNoParams<Joke>

internal class GetRandomJokeUseCaseImpl(
    private val repository: JokeRepository,
) : GetRandomJokeUseCase {

    override suspend fun invoke(): Result<Joke> =
        repository.getRandomJoke()
}
