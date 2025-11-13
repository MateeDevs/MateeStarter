package kmp.shared.analytics.data.repository

import kmp.shared.analytics.data.provider.AnalyticsProvider
import kmp.shared.analytics.domain.model.AnalyticsEvent
import kmp.shared.analytics.domain.repository.AnalyticsRepository
import kmp.shared.base.domain.model.Result

internal class AnalyticsRepositoryImpl(
    private val analyticsProvider: AnalyticsProvider,
) : AnalyticsRepository {
    override fun logEvent(event: AnalyticsEvent): Result<Unit> =
        analyticsProvider.logEvent(event)
}
