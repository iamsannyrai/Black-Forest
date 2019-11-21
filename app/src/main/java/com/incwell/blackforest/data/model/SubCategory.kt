package com.incwell.blackforest.data.model

data class SubCategory(
    val name: String,
    var items: List<SubCategoryItem>,
    val id: Int,
    val sub_category_image: String,
    var status: Boolean
)