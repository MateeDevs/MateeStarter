package kmp.android.shared.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.ui.unit.IntOffset
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import kmp.android.shared.ui.utils.Material3Easings

/** Transition spec for switching tabs */
val TopLevelTransitionSpec =
    NavDisplay.transitionSpec { topLevelEnter() togetherWith topLevelExit() }

fun mateeTransitionSpec(): AnimatedContentTransitionScope<*>.() -> ContentTransform = {
    ContentTransform(
        enter(),
        exit(),
    )
}

fun mateePopTransitionSpec(): AnimatedContentTransitionScope<*>.() -> ContentTransform = {
    ContentTransform(
        popEnter(),
        popExit(),
    )
}

fun mateePredictivePopTransitionSpec(): AnimatedContentTransitionScope<*>.(@NavigationEvent.SwipeEdge Int) -> ContentTransform = {
    ContentTransform(
        popEnter(),
        popExit(),
    )
}

fun AnimatedContentTransitionScope<*>.enter() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Start,
    animationSpec = slideInEffect,
    initialOffset = offsetFuncHorizontal,
) + fadeIn(animationSpec = fadeInEffect)

fun AnimatedContentTransitionScope<*>.exit() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Start,
    animationSpec = slideOutEffect,
    targetOffset = offsetFuncHorizontal,
)

fun AnimatedContentTransitionScope<*>.popEnter() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.End,
    animationSpec = slideInEffect,
    initialOffset = offsetFuncHorizontal,
)

fun AnimatedContentTransitionScope<*>.popExit() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.End,
    animationSpec = slideOutEffect,
    targetOffset = offsetFuncHorizontal,
) + fadeOut(animationSpec = fadeOutEffect)

fun AnimatedContentTransitionScope<*>.topLevelEnter() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Up,
    animationSpec = topLevelSlideInEffect,
    initialOffset = offsetFuncVertical,
) + fadeIn(animationSpec = topLevelFadeInEffect)

fun AnimatedContentTransitionScope<*>.topLevelExit() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Down,
    animationSpec = topLevelSlideOutEffect,
    targetOffset = offsetFuncVertical,
) + fadeOut(animationSpec = topLevelFadeOutEffect)

fun dialogEnter() = fadeIn(animationSpec = fadeInEffect)

fun dialogExit() = fadeOut(animationSpec = fadeOutEffect)

private const val FADE_IN_DELAY_MS = 50
private const val FADE_OUT_DELAY_MS = 35
private const val FADE_DURATION_MS = 80
private const val SLIDE_DURATION_MS = 450

private val slideInEffect =
    tween<IntOffset>(
        durationMillis = SLIDE_DURATION_MS,
        easing = Material3Easings.Emphasized,
    )

private val slideOutEffect =
    tween<IntOffset>(
        durationMillis = SLIDE_DURATION_MS,
        easing = Material3Easings.Emphasized,
    )

private val fadeInEffect =
    tween<Float>(
        durationMillis = FADE_DURATION_MS,
        delayMillis = FADE_IN_DELAY_MS,
        easing = LinearEasing,
    )

private val fadeOutEffect =
    tween<Float>(
        durationMillis = FADE_DURATION_MS,
        delayMillis = FADE_OUT_DELAY_MS,
        easing = LinearEasing,
    )

private val offsetFuncHorizontal: (offsetForFullSlide: Int) -> Int = { it.div(10) }

private const val TOP_LEVEL_FADE_IN_DELAY_MS = 100
private const val TOP_LEVEL_FADE_DURATION_MS = 150
private const val TOP_LEVEL_SLIDE_DURATION_MS = 450

private val topLevelSlideInEffect =
    tween<IntOffset>(
        durationMillis = TOP_LEVEL_SLIDE_DURATION_MS,
        easing = FastOutSlowInEasing,
    )

private val topLevelSlideOutEffect =
    tween<IntOffset>(
        durationMillis = TOP_LEVEL_SLIDE_DURATION_MS,
        easing = FastOutSlowInEasing,
    )

private val topLevelFadeInEffect =
    tween<Float>(
        durationMillis = TOP_LEVEL_FADE_DURATION_MS,
        delayMillis = TOP_LEVEL_FADE_IN_DELAY_MS,
        easing = LinearEasing,
    )

private val topLevelFadeOutEffect =
    tween<Float>(
        durationMillis = TOP_LEVEL_FADE_DURATION_MS,
        easing = LinearEasing,
    )

private val offsetFuncVertical: (offsetForFullSlide: Int) -> Int = { it.div(20) }
