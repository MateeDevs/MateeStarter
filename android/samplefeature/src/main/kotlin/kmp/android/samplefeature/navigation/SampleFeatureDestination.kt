package kmp.android.samplefeature.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface SampleFeatureNavKey : NavKey

@Serializable
data object SampleFeatureHome : SampleFeatureNavKey