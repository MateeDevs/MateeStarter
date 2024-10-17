package kmp.shared.analytics.domain.usecase

import kmp.shared.analytics.domain.model.AnalyticsEvent
import kmp.shared.analytics.domain.repository.AnalyticsRepository
import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseResult
import kmp.shared.base.util.extension.success

interface TrackAnalyticsEventUseCase: UseCaseResult<TrackAnalyticsEventUseCase.Params, Unit> {
    data class Params(val event: AnalyticsEvent)
}

internal class TrackAnalyticsEventUseCaseImpl(
    private val repository: AnalyticsRepository,
) : TrackAnalyticsEventUseCase {
    override suspend fun invoke(params: TrackAnalyticsEventUseCase.Params): Result<Unit> {
        return repository.logEvent(params.event).success()
    }
}