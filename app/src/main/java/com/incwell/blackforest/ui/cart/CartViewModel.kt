package com.incwell.blackforest.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.UpdateItem
import com.incwell.blackforest.data.repository.CartRepository
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _cartData = MutableLiveData<List<CartItem>>()
    val cartData: LiveData<List<CartItem>>
        get() = _cartData

    private val _addToCartResponse = MutableLiveData<Boolean>()
    val addToCartResponse: LiveData<Boolean>
        get() = _addToCartResponse

    private val _removeFromCartResponse = MutableLiveData<Boolean>()
    val removeFromCartResponse: LiveData<Boolean>
        get() = _removeFromCartResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    var cartId: Int? = null


    var incDecStatus: Boolean = false

    fun getCartItem() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = cartRepository.getCartItemFromServer()
                if (res.isSuccessful) {
                    _cartData.postValue(res.body()!!.data)
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

    fun addToCart(productId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = cartRepository.postCartItemToServer(productId)
                if (res.body()!!.status == true) {
                    _addToCartResponse.postValue(true)
                } else {
                    _addToCartResponse.postValue(false)
                }
            } catch (e: NoInternetException) {
                _errorMessage.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun removeItemFromCart(cartId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = cartRepository.deleteCartItem(cartId)
                if (res.isSuccessful) {
                    _removeFromCartResponse.postValue(true)
                } else {
                    _removeFromCartResponse.postValue(false)
                }
            } catch (e: NoInternetException) {

            }
        }
    }

    suspend fun updateCartItem(updateItem: UpdateItem, cartId: Int) {
        try {
            val res = cartRepository.updateItemQuantity(updateItem, cartId)
            if (res.isSuccessful) {
                Log.d("incDec", "${res}")
                incDecStatus = true
                Log.d("incDec1", "$incDecStatus")
            } else {
                Log.d("incDec", "${res}")
                incDecStatus = false
                Log.d("incDec1", "$incDecStatus")
            }
        } catch (e: NoInternetException) {

        }
    }
}