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
import java.net.SocketTimeoutException

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getProductDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = productRepository.getProductDetail(id)
                if (res.isSuccessful) {
                    _product.postValue(res.body()!!.data)
                } else {
                    _errorMessage.postValue(res.message())
                }
            } catch (e: NoInternetException) {
                _errorMessage.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _errorMessage.postValue(e.message)
            }
        }
    }
}