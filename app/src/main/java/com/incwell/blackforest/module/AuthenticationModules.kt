package com.incwell.blackforest.module

import com.incwell.blackforest.data.AuthenticationService
import com.incwell.blackforest.data.repository.AuthenticationRepository
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val authenticationModule:Module = module {
    viewModel { AuthenticationViewModel(get()) }
    single { AuthenticationService(get()) }
    single { AuthenticationRepository(get()) }
    single { SharedPref }
}