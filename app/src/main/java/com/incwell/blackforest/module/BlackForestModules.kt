package com.incwell.blackforest.module

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.NetworkStatusInterceptor
import com.incwell.blackforest.data.UserTokenInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module

val blackForestModule: Module = module{
    single { BlackForestService(get(),get()) }
    single { NetworkStatusInterceptor(get()) }
    single { UserTokenInterceptor() }

}