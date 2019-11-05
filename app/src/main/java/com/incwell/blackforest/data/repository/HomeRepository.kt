package com.incwell.blackforest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.storage.SharedPref
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

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getCartItemFromServer() //first get cart item from server and save into shared pref
                retrieveCategories()
                retrieveFeaturedProduct()
            } catch (e: NoInternetException) {
                Log.i(LOG_TAG, e.message!!)
            } catch (e: SocketTimeoutException) {
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

    private suspend fun getCartItemFromServer() {
        val res = blackForestService.getCartItem()
        if (res.isSuccessful) {
            val serviceData = blackForestService.getCartItem().body()?.data ?: emptyList()
            //delete all previous cart items in shared preference and save new cart from web
            SharedPref.deleteAllCartItem()
            SharedPref.saveCartItems(serviceData)
        } else {
            Log.d(LOG_TAG, "Something went wrong with getCartItemFromServer()")
        }
    }
}