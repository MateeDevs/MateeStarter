package kmp.shared.samplefeature.domain.usecase

import kmp.shared.base.domain.error.domain.CommonError
import kmp.shared.base.domain.model.Result
import kmp.shared.samplefeature.domain.model.Joke
import kmp.shared.samplefeature.domain.repository.JokeRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class GetRandomJokeUseCaseTest {

    private lateinit var mockRepository: JokeRepository
    private lateinit var useCase: GetRandomJokeUseCase

    @BeforeTest
    fun setUp() {
        mockRepository = createDefaultMockRepository()
        useCase = GetRandomJokeUseCaseImpl(mockRepository)
    }

    @Test
    fun `invoke returns success when repository returns success`() = runBlocking {
        // Given
        val expectedJoke = Joke(
            id = 1L,
            type = "programming",
            setup = "Why do programmers prefer dark mode?",
            punchline = "Because light attracts bugs!",
        )
        mockRepository = object : JokeRepository {
            override suspend fun getRandomJoke(): Result<Joke> = Result.Success(expectedJoke)
        }
        useCase = GetRandomJokeUseCaseImpl(mockRepository)

        // When
        val result = useCase()

        // Then
        assertIs<Result.Success<Joke>>(result)
        assertEquals(expectedJoke, result.data)
    }

    @Test
    fun `invoke returns error when repository returns error`() = runBlocking {
        // Given
        val expectedError = CommonError.Unknown
        mockRepository = object : JokeRepository {
            override suspend fun getRandomJoke(): Result<Joke> = Result.Error(expectedError)
        }
        useCase = GetRandomJokeUseCaseImpl(mockRepository)

        // When
        val result = useCase()

        // Then
        assertIs<Result.Error<Joke>>(result)
        assertEquals(expectedError, result.error)
        assertNull(result.data)
    }

    @Test
    fun `invoke propagates repository result correctly`() = runBlocking {
        // Given
        val testJoke = Joke(
            id = 42L,
            type = "general",
            setup = "Test setup",
            punchline = "Test punchline",
        )
        mockRepository = object : JokeRepository {
            override suspend fun getRandomJoke(): Result<Joke> = Result.Success(testJoke)
        }
        useCase = GetRandomJokeUseCaseImpl(mockRepository)

        // When
        val result = useCase()

        // Then
        assertIs<Result.Success<Joke>>(result)
        assertEquals(42L, result.data.id)
        assertEquals("general", result.data.type)
        assertEquals("Test setup", result.data.setup)
        assertEquals("Test punchline", result.data.punchline)
    }

    // Helper functions
    private fun createDefaultMockRepository(): JokeRepository {
        return object : JokeRepository {
            override suspend fun getRandomJoke(): Result<Joke> = Result.Success(
                Joke(
                    id = 0L,
                    type = "default",
                    setup = "Default setup",
                    punchline = "Default punchline",
                ),
            )
        }
    }
}
