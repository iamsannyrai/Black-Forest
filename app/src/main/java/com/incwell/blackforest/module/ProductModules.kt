package com.incwell.blackforest.module

import com.incwell.blackforest.data.repository.ProductRepository
import com.incwell.blackforest.ui.product.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productModule = module {
    viewModel { ProductViewModel(get()) }
    single { ProductRepository(get()) }
}