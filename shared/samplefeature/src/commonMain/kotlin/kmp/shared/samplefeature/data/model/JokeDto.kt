package kmp.shared.samplefeature.data.model

import kmp.shared.samplefeature.domain.model.Joke
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JokeDto(
    @SerialName("id")
    val id: Long,
    @SerialName("type")
    val type: String,
    @SerialName("setup")
    val setup: String,
    @SerialName("punchline")
    val punchline: String,
)

internal fun JokeDto.toDomain(): Joke =
    Joke(id = id, type = type, setup = setup, punchline = punchline)
