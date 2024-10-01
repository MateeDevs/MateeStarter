package kmp.android.extension

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.TouchInjectionScope
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeUp
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun ComposeContentTestRule.waitUntil(
    timeout: Duration = 1.seconds,
    condition: () -> Boolean,
) {
    waitUntil(timeoutMillis = timeout.inWholeMilliseconds, condition = condition)
}

fun ComposeContentTestRule.waitUntilNodeCount(
    matcher: SemanticsMatcher,
    count: Int,
    timeout: Duration = 1.seconds,
) {
    waitUntil(timeout.inWholeMilliseconds) {
        onAllNodes(matcher).fetchSemanticsNodes().size == count
    }
}

fun ComposeContentTestRule.waitUntilExactlyOneExists(
    matcher: SemanticsMatcher,
    timeout: Duration = 1.seconds,
) {
    return this.waitUntilNodeCount(matcher, 1, timeout)
}

fun ComposeContentTestRule.waitUntilDoesNotExist(
    matcher: SemanticsMatcher,
    timeout: Duration = 1.seconds,
) {
    return this.waitUntilNodeCount(matcher, 0, timeout)
}

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilAtLeastOneExists(
    matcher: SemanticsMatcher,
    timeout: Duration = 1.seconds,
) {
    return this.waitUntilAtLeastOneExists(matcher, timeout.inWholeMilliseconds)
}

/**
 * For LazyColumns and LazyRows the items are loaded lazily, especially if paging is used,
 * this method scrolls through the items until a matching one is found
 * @param scrollableParent lazy scrollable parent node on which we can scroll to reach children
 * @param scroll swipes the parent view in given direction, e.g. [TouchInjectionScope.swipeLeft]
 * @param matcher what do we expect at least one of the parent's children to match
 * @param timeout max duration we allow this check to take
 */
fun ComposeContentTestRule.findChildInLazyList(
    scrollableParent: SemanticsNodeInteraction,
    scroll: TouchInjectionScope.() -> Unit,
    matcher: SemanticsMatcher,
    timeout: Duration = LongDuration,
) {
    try {
        scrollableParent
            .onChildren()
            .assertAny(matcher)
    } catch (e: AssertionError) {
        val finish = Clock.System.now().plus(timeout)
        while (Clock.System.now() <= finish) {
            try {
                scrollableParent
                    .onChildren()
                    .assertAny(matcher)
                return
            } catch (e: AssertionError) {
                scrollableParent
                    .performTouchInput { scroll() }

                waitForIdle()
            }
        }
        throw AssertionError("Failed to assertAny(${matcher.description})")
    }
}

/**
 * For LazyColumns the items are loaded lazily, especially if paging is used,
 * this method scrolls through the items until a matching one is found
 * @param scrollableParent lazy scrollable parent node on which we can scroll to reach children
 * @param matcher what do we expect at least one of the parent's children to match
 * @param timeout max duration we allow this check to take
 */
fun ComposeContentTestRule.findChildInLazyColumn(
    scrollableParent: SemanticsNodeInteraction,
    matcher: SemanticsMatcher,
    timeout: Duration = LongDuration,
) {
    findChildInLazyList(
        scrollableParent = scrollableParent,
        scroll = { swipeUp() },
        matcher = matcher,
        timeout = timeout,
    )
}

/**
 * For LazyRows the items are loaded lazily, especially if paging is used,
 * this method scrolls through the items until a matching one is found
 * @param scrollableParent lazy scrollable parent node on which we can scroll to reach children
 * @param matcher what do we expect at least one of the parent's children to match
 * @param timeout max duration we allow this check to take
 */
fun ComposeContentTestRule.findChildInLazyRow(
    scrollableParent: SemanticsNodeInteraction,
    matcher: SemanticsMatcher,
    timeout: Duration = LongDuration,
) {
    findChildInLazyList(
        scrollableParent = scrollableParent,
        scroll = { swipeLeft() },
        matcher = matcher,
        timeout = timeout,
    )
}

val ShortDuration = 10L.toDuration(DurationUnit.SECONDS)
val LongDuration = 20L.toDuration(DurationUnit.SECONDS)
val VeryLongDuration = 30L.toDuration(DurationUnit.SECONDS)