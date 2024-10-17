package kmp.shared.analytics.domain.model

/**
 * Sample analytics object showing how to define analytics events
 * and organize them in a structured way.
 */
object ToastAnalytics {

    object Event {
        fun presented(type: ViewType) = AnalyticsEvent(AnalyticName.Event.ToastPresented, mapOf(AnalyticName.Param.PresentedFrom to type.value))
    }

    enum class ViewType(val value: String) {
        Native("native"),
        SharedVM("shared_vm"),
    }
}