package com.incwell.blackforest.data

import com.incwell.blackforest.WEB_SERVICE_URL
import com.incwell.blackforest.data.model.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {

    @GET("city/")
    suspend fun getCities(): Response<List<City>>

    @POST("register/")
    suspend fun registerUser(@Body user: User): Response<BaseResponse<User>>

    @POST("login/")
    suspend fun signinUser(@Body signIn: SignIn): Response<BaseResponse<SignInResponse>>

    companion object {
        operator fun invoke(networkStatusInterceptor: NetworkStatusInterceptor): AuthenticationService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkStatusInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(AuthenticationService::class.java)
        }
    }
}