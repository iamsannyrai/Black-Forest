package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService

class ProductRepository(private val blackForestService: BlackForestService) {
    suspend fun getProductDetail(id: Int) = blackForestService.getIndividualProduct(id)
}