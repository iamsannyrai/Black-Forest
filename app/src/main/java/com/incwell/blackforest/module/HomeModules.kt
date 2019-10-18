package com.incwell.blackforest.module

import com.incwell.blackforest.data.repository.HomeRepository
import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.NetworkStatusInterceptor
import com.incwell.blackforest.data.repository.CategoryRepository
import com.incwell.blackforest.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
    single { HomeRepository(get()) }
}