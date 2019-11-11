package com.incwell.blackforest.module

import com.incwell.blackforest.data.repository.OrderRepository
import com.incwell.blackforest.ui.order.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val orderModule= module {
    viewModel { OrderViewModel(get()) }
    single { OrderRepository(get()) }
}