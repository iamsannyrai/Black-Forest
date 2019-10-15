package com.incwell.blackforest.data

import retrofit2.Response
import retrofit2.http.GET

interface BlackForestService {

    @GET("category")
    suspend fun getCategories() : Response<BaseResponse<List<Category>>>

    @GET("featured-item")
    suspend fun getFeaturedProduct() : Response<BaseResponse<List<Product>>>
}