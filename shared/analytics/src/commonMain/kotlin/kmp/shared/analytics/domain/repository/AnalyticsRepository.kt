package kmp.shared.analytics.domain.repository

import kmp.shared.analytics.domain.model.AnalyticsEvent
import kmp.shared.base.domain.model.Result

/**
 * Repository to log analytics events.
 */
internal interface AnalyticsRepository {
    fun logEvent(event: AnalyticsEvent): Result<Unit>
}
