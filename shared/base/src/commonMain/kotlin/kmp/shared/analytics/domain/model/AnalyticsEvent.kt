package kmp.shared.analytics.domain.model

/**
 * Class defining a analytics event that can be logged.
 *
 * @param eventName The type (name) of this event
 * @param parameters Parameters for this event
 */
abstract class AnalyticsEvent(
    val eventName: String,
    val parameters: Map<String, String> = emptyMap(),
)