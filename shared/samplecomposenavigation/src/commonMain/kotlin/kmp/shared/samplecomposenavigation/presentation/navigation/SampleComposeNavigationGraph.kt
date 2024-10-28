package kmp.shared.samplecomposenavigation.presentation.navigation

object SampleComposeNavigationGraph : FeatureGraph(parent = null) {

    override val path = "sampleComposeNavigation"

    data object Main : Destination(this) {
        override val routeDefinition: String = "main"
    }

    data object Next : Destination(this) {
        override val routeDefinition: String = "next"
    }
}
