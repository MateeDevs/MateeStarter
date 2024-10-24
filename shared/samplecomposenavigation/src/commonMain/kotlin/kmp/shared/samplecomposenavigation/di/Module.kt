package kmp.shared.samplecomposenavigation.di

import kmp.shared.samplecomposenavigation.presentation.vm.SampleNextViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sampleComposeNavigationModule = module {
    // View models
    viewModelOf(::SampleNextViewModel)
}
