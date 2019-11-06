package com.incwell.blackforest.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _cartResult = MutableLiveData<String>()
    val cartResult: LiveData<String>
        get() = _cartResult

    fun cartData() {
        CoroutineScope(Dispatchers.IO).launch {
            cartRepository.getCartItemFromServer()
        }
    }

    fun addToCartResult(productId:String){
        CoroutineScope(Dispatchers.IO).launch {
            val res = cartRepository.postCartItemToserver(productId)
            if (res.isSuccessful) {
                _cartResult.postValue("Item added in cart successfully!")
            } else {
                _cartResult.postValue("Error adding product in cart. Error: ${res.message()}")
            }
        }
    }
}