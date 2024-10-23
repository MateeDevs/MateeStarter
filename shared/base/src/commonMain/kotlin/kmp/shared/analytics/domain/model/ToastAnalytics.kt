package kmp.shared.analytics.domain.model

/**
 * Sample analytics object showing how to define analytics events
 * and organize them in a structured way.
 */
object ToastAnalytics {

    class ToastPresentedEvent(viewType: ViewType) : AnalyticsEvent("toast_presented", mapOf("presented_from" to viewType.value))

    enum class ViewType(val value: String) {
        Native("native"),
        SharedVM("shared_vm"),
    }
}
