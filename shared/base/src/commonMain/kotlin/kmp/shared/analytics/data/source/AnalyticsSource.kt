package kmp.shared.analytics.data.source

import kmp.shared.analytics.domain.model.AnalyticsEvent

interface AnalyticsSource {
    fun logEvent(event: AnalyticsEvent)
}