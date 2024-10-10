package kmp.android.navigation

import androidx.annotation.StringRes
import kmp.android.sample.navigation.SampleGraph
import kmp.android.samplecomposemultiplatform.navigation.SampleComposeMultiplatformGraph
import kmp.android.samplesharedviewmodel.navigation.SampleSharedViewModelGraph
import kmp.shared.base.MR

enum class NavBarFeature(val route: String, @StringRes val titleRes: Int) {
    Sample(SampleGraph.rootPath, MR.strings.bottom_bar_item_1.resourceId),
    SampleSharedViewModel(SampleSharedViewModelGraph.rootPath, MR.strings.bottom_bar_item_2.resourceId),
    SampleComposeMultiplatform(SampleComposeMultiplatformGraph.rootPath, MR.strings.bottom_bar_item_3.resourceId),
}
