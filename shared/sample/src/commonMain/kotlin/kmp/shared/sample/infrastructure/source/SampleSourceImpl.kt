package kmp.shared.sample.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.base.util.extension.map
import kmp.shared.sample.data.source.SampleSource
import kmp.shared.sample.domain.model.SampleText
import kmp.shared.sample.infrastructure.model.SampleTextDto
import kmp.shared.sample.infrastructure.model.toDomain
import kmp.shared.sample.infrastructure.remote.SampleService

internal class SampleSourceImpl(
    private val service: SampleService,
) : SampleSource {

    override suspend fun getSampleText(): Result<SampleText> =
        service.getSampleText(Unit).map(SampleTextDto::toDomain)
}
