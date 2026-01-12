package kmp.android.samplefeature.navigation

import kmp.android.shared.navigation.Destination
import kmp.android.shared.navigation.FeatureGraph

object SampleFeatureGraph : FeatureGraph(parent = null) {

    override val path = "sampleFeature"

    data object Main : Destination(this) {
        override val routeDefinition: String = "main"
    }
}
