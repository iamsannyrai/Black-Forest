package com.incwell.blackforest.module

import com.incwell.blackforest.SearchViewModel
import com.incwell.blackforest.data.repository.SearchRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel(get()) }
    single { SearchRepository(get()) }
}