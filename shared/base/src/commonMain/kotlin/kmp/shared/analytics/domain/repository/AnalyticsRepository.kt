package kmp.shared.analytics.domain.repository

import kmp.shared.analytics.domain.model.AnalyticsEvent

/**
 * Repository to log analytics events.
 */
internal interface AnalyticsRepository {
    fun logEvent(event: AnalyticsEvent)
}
