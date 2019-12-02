package com.incwell.blackforest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class HomeRepository(private val blackForestService: BlackForestService) {

    private val _featuredData = MutableLiveData<List<Product>>()
    val featuredData: LiveData<List<Product>>
        get() = _featuredData

    private val _categoryData = MutableLiveData<List<Category>>()
    val categoryData: LiveData<List<Category>>
        get() = _categoryData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                retrieveCategories()
                retrieveFeaturedProduct()
            } catch (e: NoInternetException) {
                _errorMessage.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    private suspend fun retrieveCategories() {
        val res = blackForestService.getCategories()
        if (res.isSuccessful) {
            val serviceData = blackForestService.getCategories().body()?.data ?: emptyList()
            _categoryData.postValue(serviceData)
        } else {
            val responseMessage = blackForestService.getCategories().message()
            _errorMessage.postValue(responseMessage)
        }
    }

    private suspend fun retrieveFeaturedProduct() {
        val res = blackForestService.getFeaturedProduct()
        if (res.isSuccessful) {
            val serviceData = blackForestService.getFeaturedProduct().body()?.data ?: emptyList()
            _featuredData.postValue(serviceData)
        } else {
            val responseMessage = blackForestService.getFeaturedProduct().message()
            _errorMessage.postValue(responseMessage)
        }
    }
}