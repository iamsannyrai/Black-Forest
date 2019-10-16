package com.incwell.blackforest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.BlackForestRepository
import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.Product

class HomeViewModel(blackForestService: BlackForestService) : ViewModel() {

    private val dataRepo = BlackForestRepository(blackForestService)

    val featuredData = dataRepo.featuredData
    val categoryData = dataRepo.categoryData

    val selectedProduct = MutableLiveData<Product>()
}