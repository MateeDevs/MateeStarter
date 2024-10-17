package kmp.shared.analytics.domain.model

/**
 * Abstract class defining
 * a analytics event that can be logged.
 *
 * @param eventName The type (name) of this event
 * @param parameters Parameters for this event
 */
class AnalyticsEvent(
    val eventName: AnalyticName.Event,
    val parameters: Map<AnalyticName.Param, String> = emptyMap()
)

/**
 * Names used to identify certain action reported to analytics
 */
object AnalyticName {
    enum class Event(val eventName: String) {
        ToastPresented("toast_presented"),
    }

    enum class Param(val parameterName: String) {
        PresentedFrom("presented_from"),
    }
}

