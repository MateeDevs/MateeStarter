package kmp.shared.base.analytics

import co.touchlab.kermit.Logger
import kmp.shared.analytics.data.source.AnalyticsSource
import kmp.shared.analytics.domain.model.AnalyticsEvent

/**
 * Android implementation of [AnalyticsSource].
 */
class AndroidAnalyticsSourceImpl : AnalyticsSource {
    override fun logEvent(event: AnalyticsEvent) {
        // TODO: Implement analytics event logging
        Logger.e("Analytics not implemented", null, "AndroidAnalyticsSourceImpl")
    }
}
