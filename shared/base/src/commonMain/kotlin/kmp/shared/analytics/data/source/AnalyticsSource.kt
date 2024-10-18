package kmp.shared.analytics.data.source

import kmp.shared.analytics.domain.model.AnalyticsEvent

/**
 * Source to log analytics events.
 * This interface is implemented by platform-specific sources.
 * @see kmp.shared.base.analytics.AndroidAnalyticsSourceImpl
 */
interface AnalyticsSource {
    fun logEvent(event: AnalyticsEvent)
}
