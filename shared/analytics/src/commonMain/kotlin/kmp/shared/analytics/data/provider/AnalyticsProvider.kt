package kmp.shared.analytics.data.provider

import kmp.shared.analytics.domain.model.AnalyticsEvent
import kmp.shared.base.domain.model.Result

/**
 * Provider to log analytics events.
 * This interface is implemented by platform-specific sources.
 * @see AndroidAnalyticsProviderImpl
 */
interface AnalyticsProvider {
    fun logEvent(event: AnalyticsEvent): Result<Unit>
}
