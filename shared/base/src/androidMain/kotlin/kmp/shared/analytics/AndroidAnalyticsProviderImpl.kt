package kmp.shared.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import kmp.shared.analytics.data.provider.AnalyticsProvider
import kmp.shared.analytics.domain.model.AnalyticsEvent

/**
 * Android implementation of [AnalyticsProvider].
 */
class AndroidAnalyticsProviderImpl : AnalyticsProvider {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.eventName) {
            event.parameters.forEach { (key, value) ->
                param(key, value)
            }
        }
    }
}
