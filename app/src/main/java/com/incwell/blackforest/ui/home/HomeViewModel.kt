package com.incwell.blackforest.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.incwell.blackforest.data.BlackForestRepository

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val dataRepo = BlackForestRepository(app)
    val featuredData = dataRepo.featuredData
    val categoryData = dataRepo.categoryData
}