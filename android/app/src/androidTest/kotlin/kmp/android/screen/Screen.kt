package kmp.android.screen

/**
 * Base interface for any screen that provides functions for testing on it
 */
internal interface Screen

/**
 * A function which brings an [implementation] of a [Screen] into scope to call [action]s on it.
 * This function should not be used directly in code, but rather as a helper function when
 * creating new [Screen]s.
 */
internal fun <B : Screen> onScreen(
    implementation: B,
    action: B.() -> Unit,
) {
    with(implementation) {
        action()
    }
}