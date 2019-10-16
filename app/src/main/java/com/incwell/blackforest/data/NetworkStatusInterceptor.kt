package com.incwell.blackforest.data

import android.content.Context
import android.net.ConnectivityManager
import com.incwell.blackforest.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkStatusInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkAvailable())
            throw NoInternetException("Make sure you have Internet Connection!")
        return chain.proceed(chain.request())
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}