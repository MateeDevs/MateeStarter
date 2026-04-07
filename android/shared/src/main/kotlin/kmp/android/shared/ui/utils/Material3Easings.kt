package kmp.android.shared.ui.utils

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.PathEasing
import androidx.compose.ui.graphics.Path

object Material3Easings {
    
    /**
     * Emphasized (The equivalent of FastOutExtraSlowIn)
     * Best for: Elements moving around on screen, or changes in state.
     */
    val Emphasized = PathEasing(
        path = Path().apply {
            moveTo(0f, 0f)
            cubicTo(0.05f, 0f, 0.133333f, 0.06f, 0.166666f, 0.4f)
            cubicTo(0.208333f, 0.82f, 0.25f, 1f, 1f, 1f)
        }
    )

    /**
     * Emphasized Decelerate
     * Best for: Elements entering the screen. 
     * Starts fast, slows down smoothly to draw attention.
     */
    val EmphasizedDecelerate = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)

    /**
     * Emphasized Accelerate
     * Best for: Elements exiting the screen. 
     * Speeds up quickly to get out of the user's way.
     */
    val EmphasizedAccelerate = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
}