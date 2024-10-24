package kmp.android.navigation

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kmp.android.sample.navigation.SampleGraph
import kmp.android.samplecomposemultiplatform.navigation.SampleComposeMultiplatformGraph
import kmp.android.samplesharedviewmodel.navigation.SampleSharedViewModelGraph
import kmp.shared.base.MR

enum class NavBarFeature(val route: String, val titleRes: StringDesc) {
    Sample(SampleGraph.rootPath, MR.strings.bottom_bar_item_1.desc()),
    SampleSharedViewModel(SampleSharedViewModelGraph.rootPath, MR.strings.bottom_bar_item_2.desc()),
    SampleComposeMultiplatform(SampleComposeMultiplatformGraph.rootPath, MR.strings.bottom_bar_item_3.desc()),
}
