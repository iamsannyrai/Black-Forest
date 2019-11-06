package com.incwell.blackforest.module

import com.incwell.blackforest.data.repository.CartRepository
import com.incwell.blackforest.ui.cart.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cartModule= module {
    viewModel { CartViewModel(get()) }
    single { CartRepository(get())}
}