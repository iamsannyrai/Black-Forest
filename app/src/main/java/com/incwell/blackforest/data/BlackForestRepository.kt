package com.incwell.blackforest.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlackForestRepository(private val blackForestService: BlackForestService) {

    private val _featuredData = MutableLiveData<List<Product>>()
    val featuredData: LiveData<List<Product>>
        get() = _featuredData

    private val _categoryData = MutableLiveData<List<Category>>()
    val categoryData: LiveData<List<Category>>
        get() = _categoryData


    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                retrieveCategories()
                retrieveFeaturedProduct()
            } catch (e: NoInternetException) {
                Log.i(LOG_TAG, e.message!!)
            }
        }
    }

    private suspend fun retrieveCategories() {
        val res = blackForestService.getCategories().isSuccessful
        if (res) {
            val serviceData = blackForestService.getCategories().body()?.data ?: emptyList()
            _categoryData.postValue(serviceData)
        }
    }

    private suspend fun retrieveFeaturedProduct() {
        val res = blackForestService.getFeaturedProduct().isSuccessful
        if (res) {
            val serviceData = blackForestService.getFeaturedProduct().body()?.data ?: emptyList()
            _featuredData.postValue(serviceData)
        }
    }
}