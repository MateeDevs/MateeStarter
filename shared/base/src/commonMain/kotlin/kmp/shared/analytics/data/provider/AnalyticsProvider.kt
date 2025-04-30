package kmp.shared.analytics.data.provider

import kmp.shared.analytics.domain.model.AnalyticsEvent

/**
 * Provider to log analytics events.
 * This interface is implemented by platform-specific sources.
 * @see kmp.shared.base.analytics.AndroidAnalyticsProviderImpl
 */
interface AnalyticsProvider {
    fun logEvent(event: AnalyticsEvent)
}
