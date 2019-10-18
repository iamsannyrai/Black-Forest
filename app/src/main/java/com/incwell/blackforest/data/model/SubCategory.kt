package com.incwell.blackforest.data.model

data class SubCategory(
    var image: String,
    var sub_category: String,
    var id: Int,
    var products: List<SubCategoryProduct>
)