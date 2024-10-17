package kmp.shared.analytics.domain.model

object UserAnalytics {

    object Event {
        fun viewUserDetail(userId: String) = AnalyticsEvent(
            AnalyticName.Event.ViewUserDetail, mapOf(
                AnalyticName.Param.Id to userId))
    }
}