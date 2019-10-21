package com.incwell.blackforest.data.model

data class Search(
    val search_for: String,
    val data_found: List<SearchedProduct>,
    val products: List<SearchedProduct>
)