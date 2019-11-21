package com.incwell.blackforest.data.model

data class Product(
    val main_image: String,
    val id: Int,
    val price: String,
    val other_images: List<OtherImages>,
    val description: String,
    val name: String,
    val ingredeint: List<String>,
    val sub_category: List<String>,
    val tags: List<String>,
    var is_feature: Boolean,
    var status: Boolean
)