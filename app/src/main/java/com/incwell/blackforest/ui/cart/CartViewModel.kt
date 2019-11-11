package com.incwell.blackforest.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.UpdateItem
import com.incwell.blackforest.data.repository.CartRepository
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _cartResult = MutableLiveData<String>()
    val cartResult: LiveData<String>
        get() = _cartResult

    fun cartData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                cartRepository.getCartItemFromServer()
            } catch (e: NoInternetException) {

            }
        }
    }

    fun addToCartResult(productId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = cartRepository.postCartItemToserver(productId)
                if (res.isSuccessful) {
                    _cartResult.postValue("Item added in cart successfully!")
                } else {
                    _cartResult.postValue("Error adding product in cart. Error: ${res.message()}")
                }
            } catch (e: NoInternetException) {

            }
        }
    }

    fun removeItemFromCart(productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = cartRepository.deleteCartItem(productId)
                if (res.isSuccessful) {
                    _cartResult.postValue("removed")
                } else {
                    _cartResult.postValue("not removed")
                }
            } catch (e: NoInternetException) {

            }
        }
    }

    fun updateCartItem(updateItem: UpdateItem, cartId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = cartRepository.updateItemQuantity(updateItem, cartId)
                if (res.isSuccessful) {
                    Log.d("incDec", "updated")
                } else {
                    Log.d("incDec", "failed")
                }
            } catch (e: NoInternetException) {

            }
        }
    }
}