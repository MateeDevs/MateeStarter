package kmp.shared.samplecomposemultiplatform.di

import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sampleComposeMultiplatformModule = module {
    // View models
    viewModelOf(::SampleNextViewModel)
}
