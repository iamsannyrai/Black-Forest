package com.incwell.blackforest.module

import com.incwell.blackforest.data.repository.CategoryRepository
import com.incwell.blackforest.ui.category.subCategory.SubCategoryFragment
import com.incwell.blackforest.ui.category.subCategory.SubCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule= module {
    viewModel { SubCategoryViewModel(get()) }
    single { CategoryRepository(get()) }
    single { SubCategoryFragment() }
}