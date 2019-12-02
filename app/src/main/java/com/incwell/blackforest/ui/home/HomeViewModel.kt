package com.incwell.blackforest.ui.home

import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.repository.HomeRepository

class HomeViewModel(homeRepository: HomeRepository) : ViewModel() {
    val featuredData = homeRepository.featuredData
    val categoryData = homeRepository.categoryData
    val errorMessage = homeRepository.errorMessage
}