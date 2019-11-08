package com.incwell.blackforest.data

import androidx.annotation.WorkerThread
import com.incwell.blackforest.WEB_SERVICE_URL
import com.incwell.blackforest.data.model.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface BlackForestService {

    @GET("category/")
    suspend fun getCategories(): Response<BaseResponse<List<Category>>>

    @GET("featured-item/")
    suspend fun getFeaturedProduct(): Response<BaseResponse<List<Product>>>

    @GET("category/{id}")
    suspend fun getSubcategories(@Path("id") id: Int): Response<BaseResponse<List<SubCategory>>>

    @GET("sub-category/{id}")
    suspend fun getSubcategoyProducts(@Path("id") id: Int): Response<BaseResponse<List<Product>>>

    @GET("search")
    suspend fun getSearchedProducts(@Query("search-for") tag: String): Response<BaseResponse<List<Search>>>

    @POST("google")
    suspend fun sendGoogleAccessToken(@Body access_token:AccessToken):Response<BaseResponse<AccessToken>>

    @POST("register/")
    suspend fun registerUser(@Body user:User):Response<BaseResponse<User>>

    @POST("login/")
    suspend fun signinUser(@Body signIn: SignIn):Response<BaseResponse<SignInResponse>>

    @GET("add-to-cart")
    suspend fun getCartItem(): Response<BaseResponse<List<CartItem>>>

    @POST("add-to-cart/")
    suspend fun postCartItem(@Body productId: ProductID):Response<BaseResponse<List<CartItem>>>

    @DELETE("add-to-cart/{id}/")
    suspend fun removeCartItem(@Path("id") id: Int) : Response<BaseResponse<String>>

    @WorkerThread
    companion object {
        operator fun invoke(
            networkStatusInterceptor: NetworkStatusInterceptor,
            userTokenInterceptor: UserTokenInterceptor
        ): BlackForestService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkStatusInterceptor)
                .addInterceptor(userTokenInterceptor)
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