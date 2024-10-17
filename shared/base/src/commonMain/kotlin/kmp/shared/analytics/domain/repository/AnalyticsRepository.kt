package kmp.shared.analytics.domain.repository

import kmp.shared.analytics.domain.model.AnalyticsEvent

internal interface AnalyticsRepository {
    fun logEvent(event: AnalyticsEvent)
}