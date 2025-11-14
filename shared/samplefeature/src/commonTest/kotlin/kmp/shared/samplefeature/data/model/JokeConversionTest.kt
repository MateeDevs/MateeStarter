package kmp.shared.samplefeature.data.model

import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress(
    "konsist.all DTOs are internal or private",
    "konsist.all classes in 'data model' package are annotated with Serializable",
)
class JokeConversionTest {

    @Test
    fun `toDomain converts JokeDto to Joke with correct data`() {
        val jokeDto = JokeDto(
            id = 456L,
            type = "programming",
            setup = "Why do programmers prefer dark mode?",
            punchline = "Because light attracts bugs!",
        )

        val joke = jokeDto.toDomain()

        assertEquals(456L, joke.id)
        assertEquals("programming", joke.type)
        assertEquals("Why do programmers prefer dark mode?", joke.setup)
        assertEquals("Because light attracts bugs!", joke.punchline)
    }

    @Test
    fun `toDomain preserves empty strings`() {
        val jokeDto = JokeDto(
            id = 789L,
            type = "",
            setup = "",
            punchline = "",
        )

        val joke = jokeDto.toDomain()

        assertEquals(789L, joke.id)
        assertEquals("", joke.type)
        assertEquals("", joke.setup)
        assertEquals("", joke.punchline)
    }
}
