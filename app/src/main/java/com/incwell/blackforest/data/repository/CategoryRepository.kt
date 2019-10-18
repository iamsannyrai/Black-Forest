package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService

class CategoryRepository(private val blackForestService: BlackForestService) {
    suspend fun getSubCategories(categoryId:Int) = blackForestService.getSubcategories(categoryId)
    suspend fun getSubCategoryProducts(subCategoryId:Int) = blackForestService.getSubcategoyProducts(subCategoryId)
}