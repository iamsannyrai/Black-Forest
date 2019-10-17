package com.incwell.blackforest.data

import androidx.annotation.WorkerThread
import com.incwell.blackforest.WEB_SERVICE_URL
import com.incwell.blackforest.data.model.BaseResponse
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.Product
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface BlackForestService {

    @GET("category")
    suspend fun getCategories(): Response<BaseResponse<List<Category>>>

    @GET("featured-item")
    suspend fun getFeaturedProduct(): Response<BaseResponse<List<Product>>>

    @WorkerThread
    companion object {
        operator fun invoke(
            networkStatusInterceptor: NetworkStatusInterceptor
        ): BlackForestService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkStatusInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(BlackForestService::class.java)
        }
    }
}