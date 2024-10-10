package kmp.shared.samplesharedviewmodel.di

import kmp.shared.samplesharedviewmodel.vm.SampleSharedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sampleSharedViewModelModule = module {
    // View models
    viewModelOf(::SampleSharedViewModel)
}
