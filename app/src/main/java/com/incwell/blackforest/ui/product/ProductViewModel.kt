package com.incwell.blackforest.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.repository.ProductRepository
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    fun getProductDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = productRepository.getProductDetail(id)
                if (res.isSuccessful) {
                    Log.d("productDetail", "${res.body()!!.data}")
                    _product.postValue(res.body()!!.data)
                }
            } catch (e: NoInternetException) {

            }
        }
    }
}