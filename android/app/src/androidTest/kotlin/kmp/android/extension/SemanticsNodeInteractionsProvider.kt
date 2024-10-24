package kmp.android.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.TouchInjectionScope
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.click
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isFocusable
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import kmp.shared.samplecomposenavigation.presentation.ui.test.TestTag

/**
 * Finds an focusable node in a sub tree whose root matches the [matcher]
 * @return a node which is a child of a node matching [matcher] and is focusable.
 */
fun SemanticsNodeInteractionsProvider.onEditableWith(matcher: SemanticsMatcher): SemanticsNodeInteraction =
    onNode(matcher)
        .onChildren()
        .filterToOne(isFocusable())

/**
 * Asserts the node contains [color] anywhere in it's bounds.
 */
fun SemanticsNodeInteraction.assertContainsColor(color: Color): SemanticsNodeInteraction {
    val imageBitmap = captureToImage()
    val pixelColors = imageBitmap.toPixelMap()
    (0 until imageBitmap.width).forEach { x ->
        (0 until imageBitmap.height).forEach { y ->
            if (pixelColors[x, y] == color) return this
        }
    }
    throw AssertionError("Assert failed: The component does not contain the color")
}

/**
 * Asserts the node does not contain [color] anywhere in it's bounds.
 */
fun SemanticsNodeInteraction.assertDoesNotContainColor(color: Color): SemanticsNodeInteraction {
    val imageBitmap = captureToImage()
    val pixelColors = imageBitmap.toPixelMap()
    (0 until imageBitmap.width).forEach { x ->
        (0 until imageBitmap.height).forEach { y ->
            if (pixelColors[x, y] == color) throw AssertionError("Assert failed: The component contains the color")
        }
    }
    return this
}

/**
 * Finds a semantics node identified by the given tag.
 *
 * For usage patterns and semantics concepts see [SemanticsNodeInteraction]
 *
 * @param useUnmergedTree Find within merged composables like Buttons.
 *
 * @see SemanticsNodeInteractionsProvider.onNode for general find method.
 */
fun SemanticsNodeInteractionsProvider.onNodeWithTag(
    testTag: TestTag,
    useUnmergedTree: Boolean = false,
): SemanticsNodeInteraction = onNodeWithTag(testTag.tag, useUnmergedTree)

/**
 * Finds all semantics nodes identified by the given tag.
 *
 * For usage patterns and semantics concepts see [SemanticsNodeInteraction]
 *
 * @param useUnmergedTree Find within merged composables like Buttons.
 *
 * @see SemanticsNodeInteractionsProvider.onAllNodes for general find method.
 */
fun SemanticsNodeInteractionsProvider.onAllNodesWithTag(
    testTag: TestTag,
    useUnmergedTree: Boolean = false,
): SemanticsNodeInteractionCollection = onAllNodesWithTag(testTag.tag, useUnmergedTree)

fun hasTestTag(testTag: TestTag): SemanticsMatcher = hasTestTag(testTag.tag)

// helping function for debugging
fun SemanticsNodeInteractionsProvider.printSemanticTree() {
    onAllNodes(SemanticsMatcher("") { true }).printToLog("test")
}

fun SemanticsNodeInteractionCollection.assertAllDisplayed(): SemanticsNodeInteractionCollection {
    fetchSemanticsNodes().forEachIndexed { index, _ ->
        get(index).assertIsDisplayed()
    }
    return this
}

fun SemanticsNodeInteractionCollection.assertAtLeastOneDisplayed(): SemanticsNodeInteractionCollection {
    val displayed = fetchSemanticsNodes().filterIndexed { index, _ ->
        try {
            get(index).assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }
    if (displayed.isEmpty()) {
        throw AssertionError("Assert failed: None of the components is displayed!")
    }
    return this
}

fun SemanticsNodeInteraction.performClick(position: TouchInjectionScope.() -> Offset = { center }): SemanticsNodeInteraction {
    return performTouchInput {
        click(position = position())
    }
}