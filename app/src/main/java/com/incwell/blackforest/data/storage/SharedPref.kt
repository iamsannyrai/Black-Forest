package com.incwell.blackforest.data.storage

import android.util.Log
import com.incwell.blackforest.CART_ITEM
import com.incwell.blackforest.CITY
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.City
import com.incwell.blackforest.data.model.Product
import com.orhanobut.hawk.Hawk

interface SharedPref {
    companion object {

        //functions for maintaining cities
        fun saveCity(city: City) {
            val cities = getCity()
            val cityItem = City(city.id, city.city)
            cities.add(cityItem)
            Hawk.put(CITY, cities)

        }

        fun getCity(): ArrayList<City> {
            return if (Hawk.get<ArrayList<City>>(CITY) == null) ArrayList() else Hawk.get<ArrayList<City>>(
                CITY
            )
        }

        fun resetCity() {
            Hawk.delete(CITY)
        }

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
            Log.d("productIDD","${product.id}")
            val products = getCart()
            val cartItem = CartItem(product.name, product.price, null, product.main_image,product_id = product.id)
            products.add(cartItem)
            Hawk.put(CART_ITEM, products)

        }

        fun getCart(): ArrayList<CartItem> {
            return if (Hawk.get<ArrayList<CartItem>>(CART_ITEM) != null) {
                Hawk.get<ArrayList<CartItem>>(CART_ITEM)
            } else {
                ArrayList()
            }
        }

        fun saveCartItems(cartItems: ArrayList<CartItem>) {
            Hawk.put(CART_ITEM, cartItems)
        }
    }
}