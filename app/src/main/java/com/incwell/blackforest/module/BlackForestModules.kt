package com.incwell.blackforest.module

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.NetworkStatusInterceptor
import org.koin.dsl.module

val blackForestModule= module {
    single { BlackForestService(get()) }
    single { NetworkStatusInterceptor(get()) }
}