package com.incwell.blackforest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.ProductID
import com.incwell.blackforest.data.storage.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartRepository(private val blackForestService: BlackForestService) {

    var cartData = ArrayList<CartItem>()

    suspend fun getCartItemFromServer() {
        val res = blackForestService.getCartItem()
        if (res.isSuccessful) {
            val serviceData: List<CartItem> = res.body()!!.data ?: emptyList()
            cartData.clear()
            SharedPref.deleteAllCartItem()
            for (i in serviceData.indices) {
                cartData.add(serviceData[i])
                SharedPref.saveCartItems(cartData)
            }
        } else {
            Log.d(LOG_TAG, "Something went wrong with getCartItemFromServer()")
        }
    }

    suspend fun postCartItemToserver(productId: String)=blackForestService.postCartItem(ProductID(productId))

    suspend fun deleteCartItem(productId: Int)=blackForestService.removeCartItem(productId)
}