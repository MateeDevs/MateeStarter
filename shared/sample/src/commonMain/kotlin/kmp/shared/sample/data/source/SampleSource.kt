package kmp.shared.sample.data.source

import kmp.shared.base.Result
import kmp.shared.sample.domain.model.SampleText

internal interface SampleSource {
    suspend fun getSampleText(): Result<SampleText>
}
