package com.incwell.blackforest.data.repository


import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.ProductID
import com.incwell.blackforest.data.model.UpdateItem


class CartRepository(private val blackForestService: BlackForestService) {

    suspend fun getCartItemFromServer() = blackForestService.getCartItem()

    suspend fun postCartItemToServer(productId: String) = blackForestService.postCartItem(ProductID(productId))

    suspend fun deleteCartItem(cartId: Int) = blackForestService.removeCartItem(cartId)

    suspend fun updateItemQuantity(updateItem: UpdateItem, cartItemId: Int) = blackForestService.updateItemQuantity(updateItem, cartItemId)
}