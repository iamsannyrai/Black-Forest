package com.incwell.blackforest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.repository.BlackForestRepository

class HomeViewModel(blackForestService: BlackForestService) : ViewModel() {

    private val dataRepo =
        BlackForestRepository(blackForestService)

    val featuredData = dataRepo.featuredData
    val categoryData = dataRepo.categoryData

    val selectedCategory = MutableLiveData<Category>()
    val selectedProduct = MutableLiveData<Product>()

}