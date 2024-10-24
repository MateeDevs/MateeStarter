package kmp.shared.base.analytics

import co.touchlab.kermit.Logger
import kmp.shared.analytics.data.provider.AnalyticsProvider
import kmp.shared.analytics.domain.model.AnalyticsEvent

/**
 * Android implementation of [AnalyticsProvider].
 */
class AndroidAnalyticsProviderImpl : AnalyticsProvider {
    override fun logEvent(event: AnalyticsEvent) {
        // TODO: Implement analytics event logging
        Logger.e("Analytics not implemented", null, "AndroidAnalyticsSourceImpl")
    }
}
