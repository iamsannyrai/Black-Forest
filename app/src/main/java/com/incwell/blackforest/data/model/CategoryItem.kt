package com.incwell.blackforest.data.model

data class CategoryItem(
    var image: String,
    var sub_category: String,
    var id: Int,
    var items: List<SubCategoryItem>
)