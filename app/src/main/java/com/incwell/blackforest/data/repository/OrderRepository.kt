package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.NewAddress
import com.incwell.blackforest.data.storage.SharedPref
import java.util.ArrayList

class OrderRepository(private val blackForestService: BlackForestService) {
    fun getCartItemsFromLocalStorage(): ArrayList<CartItem> {
        return SharedPref.getCart()
    }
    suspend fun orderProduct(newAddress:NewAddress)=blackForestService.orderProduct(newAddress)
}