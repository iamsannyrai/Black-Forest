package com.incwell.blackforest.data

import com.incwell.blackforest.tokenKey
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.Response


class UserTokenInterceptor : Interceptor {
    lateinit var userToken: String
    override fun intercept(chain: Interceptor.Chain): Response {
        userToken = Hawk.get<String>(tokenKey)
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Token $userToken")
            .build()
        return chain.proceed(newRequest)
    }
}