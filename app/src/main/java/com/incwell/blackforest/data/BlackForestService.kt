package com.incwell.blackforest.data

import androidx.annotation.WorkerThread
import com.incwell.blackforest.WEB_SERVICE_URL
import com.incwell.blackforest.data.model.*
import com.incwell.blackforest.tokenKey
import com.orhanobut.hawk.Hawk
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

interface BlackForestService {

    //home section
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
    suspend fun sendGoogleAccessToken(@Body access_token: AccessToken): Response<BaseResponse<AccessToken>>


    //cart and order section
    @GET("add-to-cart")
    suspend fun getCartItem(): Response<BaseResponse<List<CartItem>>>

    @POST("add-to-cart/")
    suspend fun postCartItem(@Body productId: ProductID): Response<BaseResponse<List<CartItem>>>

    @DELETE("add-to-cart/{id}/")
    suspend fun removeCartItem(@Path("id") id: Int): Response<BaseResponse<String>>

    @PUT("add-to-cart/{cart-id}/")
    suspend fun updateItemQuantity(@Body updateItem: UpdateItem, @Path("cart-id") id: Int): Response<BaseResponse<String>>

    @POST("orders/")
    suspend fun orderProduct(@Body newAddress: NewAddress): Response<BaseResponse<String>>


    //account section
    @GET("profile")
    suspend fun getProfile(): Response<BaseResponse<Profile>>

    @PUT("change-phone-number")
    suspend fun changePhoneNumber(@Body newPhoneNumber: PhoneNumber): Response<BaseResponse<String>>

    @PUT("password-change")
    suspend fun changePassword(@Body newPassword: NewPassword): Response<BaseResponse<String>>

    @GET("get-user")
    suspend fun getAddress(): Response<BaseResponse<CompleteProfile>>

    @PUT("change-city")
    suspend fun changeCity(@Body newCity: NewCity): Response<BaseResponse<String>>

    @PUT("change-address")
    suspend fun changeAddress(@Body newAddress: Address): Response<BaseResponse<String>>

    @GET("history")
    suspend fun getOrderHistory(): Response<BaseResponse<List<History>>>


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