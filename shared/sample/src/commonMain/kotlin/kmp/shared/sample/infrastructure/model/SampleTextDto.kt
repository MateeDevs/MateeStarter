package kmp.shared.sample.infrastructure.model

import kmp.shared.sample.domain.model.SampleText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SampleTextDto(
    @SerialName("value")
    val value: String,
)

internal fun SampleTextDto.toDomain(): SampleText =
    SampleText(value = value)
