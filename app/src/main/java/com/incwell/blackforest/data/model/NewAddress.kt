package com.incwell.blackforest.data.model

data class NewAddress(
    var status: Int = 1,
    var other_location: Boolean = false,
    val contact: String = "",
    val other_city: String? = null,
    val address: String = ""
)