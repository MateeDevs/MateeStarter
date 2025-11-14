package kmp.shared.samplefeature.domain.model

data class Joke(
    val id: Long,
    val type: String,
    val setup: String,
    val punchline: String,
)
