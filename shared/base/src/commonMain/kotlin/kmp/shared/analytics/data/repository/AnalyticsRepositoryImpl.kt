package kmp.shared.analytics.data.repository

import kmp.shared.analytics.data.source.AnalyticsSource
import kmp.shared.analytics.domain.model.AnalyticsEvent
import kmp.shared.analytics.domain.repository.AnalyticsRepository

internal class AnalyticsRepositoryImpl(
    private val analyticsSource: AnalyticsSource,
) : AnalyticsRepository {
    override fun logEvent(event: AnalyticsEvent) {
        analyticsSource.logEvent(event)
    }
}
