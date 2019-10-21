package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService

class SearchRepository(private val blackForestService: BlackForestService) {
    suspend fun retrieveSearchedProduct(tag: String) = blackForestService.getSearchedProducts(tag)
}