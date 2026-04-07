package kmp.android.shared.navigation

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavKey
import java.util.IdentityHashMap

/**
 * Navigation wrapper around the Nav3 back stack.
 * Provides common navigation patterns for the entire app.
 */
@Stable
class Navigator(
    val backStack: MutableList<NavKey>,
) {
    /** Push a new key onto the back stack. */
    fun navigate(key: NavKey) {
        backStack.add(key)
    }

    /** Push only if the top of the stack is a different key. */
    fun navigateSingleTop(key: NavKey) {
        if (backStack.lastOrNull() != key) {
            backStack.add(key)
        }
    }

    /** Pop the top entry. Returns false if already at root. */
    fun navigateUp(): Boolean {
        navigateUpOverrides[backStack]?.let { override ->
            override()
            return true
        }
        return popBackStack(backStack)
    }

    /** Clear entire stack and set a new root. */
    fun resetTo(key: NavKey) {
        backStack.clear()
        backStack.add(key)
    }

    /** Pop back to the last entry of type [T]. */
    inline fun <reified T> popBackTo(inclusive: Boolean = true) {
        val index = backStack.indexOfLast { it is T }
        if (index >= 0) {
            val removeFrom = if (inclusive) index else index + 1
            while (backStack.size > removeFrom) {
                backStack.removeAt(backStack.lastIndex)
            }
        }
    }

    /** The current (top) entry in the back stack. */
    val currentKey: NavKey? get() = backStack.lastOrNull()

    companion object {
        /**
         * Overrides keyed by backstack identity, shared across [Navigator] instances
         * that wrap the same list. Set by overlay scenes (e.g. bottom sheets) to
         * animate their dismissal before popping.
         */
        internal val navigateUpOverrides = IdentityHashMap<MutableList<NavKey>, () -> Unit>()

        /** Pop the top entry of [backStack] without triggering any override. */
        internal fun popBackStack(backStack: MutableList<NavKey>): Boolean {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
                return true
            }
            return false
        }
    }
}
