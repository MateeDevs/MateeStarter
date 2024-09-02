package kmp.shared.sample.data.repository

import kmp.shared.base.Result
import kmp.shared.sample.data.source.SampleSource
import kmp.shared.sample.domain.model.SampleText
import kmp.shared.sample.domain.repository.SampleRepository

internal class SampleRepositoryImpl(
    private val source: SampleSource,
) : SampleRepository {

    override suspend fun getSampleText(): Result<SampleText> =
        source.getSampleText()
}
