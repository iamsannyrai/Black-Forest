package com.incwell.blackforest.data.model

data class CartItem(
    val name: String,
    val price: String,
    val id: Int? = null,
    val main_image: String,
    var quantity: Int = 1,
    val product_id:Int
)