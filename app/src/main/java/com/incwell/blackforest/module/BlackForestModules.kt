package com.incwell.blackforest.module

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.NetworkStatusInterceptor
import com.incwell.blackforest.data.repository.SignupRepository
import com.incwell.blackforest.ui.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val blackForestModule= module {
    viewModel { SignupViewModel(get()) }
    single { BlackForestService(get()) }
    single { NetworkStatusInterceptor(get()) }
    single { SignupRepository(get()) }
}