package kmp.shared.analytics.domain.model

object ToastAnalytics {

    object Event {
        fun PresentedFromNative() = AnalyticsEvent(AnalyticName.Event.ToastPresented, mapOf(AnalyticName.Param.PresentedFrom to ViewType.Native.value))
        fun PresentedFromSharedVM() = AnalyticsEvent(AnalyticName.Event.ToastPresented, mapOf(AnalyticName.Param.PresentedFrom to ViewType.SharedVM.value))
        fun PresentedFromCompose() = AnalyticsEvent(AnalyticName.Event.ToastPresented, mapOf(AnalyticName.Param.PresentedFrom to ViewType.Compose.value))
    }

    private enum class ViewType(val value: String) {
        Native("native"),
        SharedVM("shared_vm"),
        Compose("compose"),
    }
}