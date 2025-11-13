package kmp.shared.analytics.data.provider

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import kmp.shared.analytics.domain.model.AnalyticsEvent
import kmp.shared.base.domain.model.Result
import kmp.shared.base.domain.util.extension.success

/**
 * Android implementation of [kmp.shared.analytics.data.provider.AnalyticsProvider].
 */
class AndroidAnalyticsProviderImpl : AnalyticsProvider {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    override fun logEvent(event: AnalyticsEvent): Result<Unit> {
        firebaseAnalytics.logEvent(event.eventName) {
            event.parameters.forEach { (key, value) ->
                param(key, value)
            }
        }
        return Unit.success()
    }
}
