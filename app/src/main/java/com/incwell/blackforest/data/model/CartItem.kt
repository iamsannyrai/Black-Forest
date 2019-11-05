package com.incwell.blackforest.data.model

data class CartItem(
    val name: String,
    val price: String,
    val id: Int,
    val main_image: String,
    var incrementRate: Int = 1,
    var quantity: Int = 1
)