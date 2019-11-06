package com.incwell.blackforest.data.storage

import android.util.Log
import com.incwell.blackforest.CART_ITEM
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.Product
import com.orhanobut.hawk.Hawk

interface SharedPref {
    companion object {

        //functions for maintaining user token
        fun saveToken(key: String, token: String) {
            Hawk.put(key, token)
        }

        fun deleteToken(key: String) {
            Hawk.delete(key)
        }

        fun checkToken(key: String): Boolean {
            return Hawk.contains(key)
        }

        //functions for maintaining cart
        fun addToCart(product: Product) {
            val products = getCart()
            val cartItem = CartItem(product.name, product.price, product.id, product.main_image)
            products.add(cartItem)
            Hawk.put(CART_ITEM, products)

        }

        fun reset() {
            Hawk.put(CART_ITEM, ArrayList<CartItem>())
        }

        fun getCart(): ArrayList<CartItem> {
            return if (Hawk.get<ArrayList<CartItem>>(CART_ITEM) != null){
                Hawk.get<ArrayList<CartItem>>(CART_ITEM)
            }else{
                ArrayList()
            }
        }

        fun saveCartItems(cartItems: ArrayList<CartItem>) {
            Hawk.put(CART_ITEM, cartItems)
        }

        fun deleteAllCartItem() {
            Hawk.delete(CART_ITEM)
            Log.d("delete","items removed")
        }
    }
}