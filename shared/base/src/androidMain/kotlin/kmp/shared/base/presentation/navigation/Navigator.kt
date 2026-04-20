package kmp.shared.base.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator

/**
 * Remembers a [Navigator] backed by a Nav3 [NavBackStack] initialized with [startKey].
 *
 * The returned navigator keeps a stable identity for the remembered back stack, while
 * [entryProviderBuilder] provides the destinations used by [Navigator.entries].
 */
@Composable
fun rememberNavigator(
    startKey: NavKey,
    entryProviderBuilder: EntryProviderScope<NavKey>.(Navigator) -> Unit,
): Navigator {
    val backStack = rememberNavBackStack(startKey)
    val currentEntryProviderBuilder = rememberUpdatedState(entryProviderBuilder)

    return remember(backStack) {
        Navigator.create(backStack) { navigator ->
            currentEntryProviderBuilder.value.invoke(this, navigator)
        }
    }
}

/** CompositionLocal used to expose the current [Navigator] to descendant composables. */
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No Navigator provided")
}

/**
 * Registers a composition-scoped callback that intercepts [Navigator.navigateUp].
 *
 * The most recently added enabled handler wins. When this composable leaves the composition,
 * its handler is removed automatically.
 */
@Composable
fun NavigateUpHandler(
    enabled: Boolean = true,
    onNavigateUp: (Navigator) -> Unit,
) {
    val navigator = LocalNavigator.current
    val currentOnNavigateUp = rememberUpdatedState(onNavigateUp)
    val handler = remember(navigator) {
        NavigateUpHandlerEntry(isEnabled = enabled) {
            currentOnNavigateUp.value(navigator)
        }
    }

    SideEffect {
        handler.isEnabled = enabled
    }

    DisposableEffect(navigator, handler) {
        navigator.addNavigateUpHandler(handler)
        onDispose {
            navigator.removeNavigateUpHandler(handler)
        }
    }
}

internal class NavigateUpHandlerEntry(
    var isEnabled: Boolean,
    val onNavigateUp: () -> Unit,
) {
    var isHandlingNavigateUp: Boolean = false
}

/**
 * Stable wrapper around the app's Nav3 back stack.
 *
 * In addition to common push/pop operations, the navigator exposes decorated [entries] for
 * `NavDisplay` and supports composition-scoped navigate-up interception via [NavigateUpHandler].
 */
@Stable
class Navigator private constructor(
    private val backStack: NavBackStack<NavKey>,
    private val entryProviderBuilder: EntryProviderScope<NavKey>.(Navigator) -> Unit,
) {
    private val navigateUpHandlers = mutableListOf<NavigateUpHandlerEntry>()

    /** Decorated Nav3 entries for the current back stack, ready to pass to `NavDisplay`. */
    val entries: List<NavEntry<NavKey>>
        @Composable get() {
            val decorators = listOf<NavEntryDecorator<NavKey>>(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            )

            val provider = entryProvider {
                entryProviderBuilder.invoke(this, this@Navigator)
            }

            return rememberDecoratedNavEntries(backStack, decorators, provider)
        }

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

    /**
     * Run the last enabled navigate-up handler that is not already handling navigate-up, or pop
     * the top entry when none are available.
     */
    fun navigateUp(): Boolean {
        navigateUpHandlers.lastOrNull { it.isEnabled && !it.isHandlingNavigateUp }?.let { handler ->
            handler.isHandlingNavigateUp = true
            try {
                handler.onNavigateUp()
            } finally {
                handler.isHandlingNavigateUp = false
            }
            return true
        }
        return popBackStack()
    }

    /** Clear the entire stack and set a new root. */
    fun resetTo(key: NavKey) {
        backStack.clear()
        backStack.add(key)
    }

    /** The current (top) entry in the back stack. */
    val currentKey: NavKey? get() = backStack.lastOrNull()

    /** Pop the top entry without consulting navigate-up handlers. Returns false at the root. */
    private fun popBackStack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            return true
        }
        return false
    }

    internal fun addNavigateUpHandler(handler: NavigateUpHandlerEntry) {
        navigateUpHandlers.add(handler)
    }

    internal fun removeNavigateUpHandler(handler: NavigateUpHandlerEntry) {
        navigateUpHandlers.remove(handler)
    }

    companion object {
        internal fun create(
            backStack: NavBackStack<NavKey>,
            entryProviderBuilder: EntryProviderScope<NavKey>.(Navigator) -> Unit,
        ): Navigator = Navigator(backStack, entryProviderBuilder)
    }
}
