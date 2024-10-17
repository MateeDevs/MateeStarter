package kmp.shared.analytics.domain.model

object LoginAnalytics {

    object Event {
        val screenAppear = AnalyticsEvent(AnalyticName.Event.LoginScreenAppear)
        val loginButtonTap = AnalyticsEvent(AnalyticName.Event.LoginButtonTap)
        val registerButtonTap = AnalyticsEvent(AnalyticName.Event.RegisterButtonTap)
    }
}