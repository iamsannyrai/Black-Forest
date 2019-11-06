package com.incwell.blackforest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.repository.HomeRepository
import com.incwell.blackforest.module.homeModule

class HomeViewModel(homeRepository: HomeRepository) : ViewModel() {

    val featuredData = homeRepository.featuredData
    val categoryData = homeRepository.categoryData

    //for sharing viewmodel
    val selectedProduct = MutableLiveData<Product>()
}