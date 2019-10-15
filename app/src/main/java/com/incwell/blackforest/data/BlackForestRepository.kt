package com.incwell.blackforest.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.WEB_SERVICE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BlackForestRepository(val app: Application) {


    private val _featuredData = MutableLiveData<List<Product>>()
    val featuredData: LiveData<List<Product>>
        get() = _featuredData

    private val _categoryData = MutableLiveData<List<Category>>()
    val categoryData: LiveData<List<Category>>
        get() = _categoryData


    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "from remote source", Toast.LENGTH_LONG).show()
            }
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val blackForestService = retrofit.create(BlackForestService::class.java)
            //get featured products
            retrieveFeaturedProduct(blackForestService)
            //get categories
            retrieveCategories(blackForestService)

        } else {
            Log.i(LOG_TAG, "Check your internet connection")
        }
    }


    private suspend fun retrieveCategories(service: BlackForestService) {
        val res = service.getCategories().isSuccessful
        if (res) {
            val serviceData = service.getCategories().body()?.data ?: emptyList()
            _categoryData.postValue(serviceData)
        }
    }

    private suspend fun retrieveFeaturedProduct(service: BlackForestService) {
        val res = service.getFeaturedProduct().isSuccessful
        if (res) {
            val serviceData = service.getFeaturedProduct().body()?.data ?: emptyList()
            _featuredData.postValue(serviceData)
        }
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

}