package com.incwell.blackforest.module

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.NetworkStatusInterceptor
import com.incwell.blackforest.data.repository.AuthenticationRepository
import com.incwell.blackforest.ui.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val blackForestModule= module {
    viewModel { AuthenticationViewModel(get()) }
    single { BlackForestService(get()) }
    single { NetworkStatusInterceptor(get()) }
    single { AuthenticationRepository(get()) }
}