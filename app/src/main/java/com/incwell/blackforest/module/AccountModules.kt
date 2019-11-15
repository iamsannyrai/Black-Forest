package com.incwell.blackforest.module

import com.incwell.blackforest.data.repository.AccountRepository
import com.incwell.blackforest.ui.account.AccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val accountModule:Module = module {
    viewModel { AccountViewModel(get()) }
    single { AccountRepository(get()) }
}