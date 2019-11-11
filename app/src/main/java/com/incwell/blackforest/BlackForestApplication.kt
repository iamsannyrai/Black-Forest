package com.incwell.blackforest

import android.app.Application
import android.content.SharedPreferences
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.module.*
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.NoEncryption
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BlackForestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        startKoin {
            androidLogger() // use AndroidLogger as Koin Logger - default Level.INFO
            androidContext(this@BlackForestApplication) // use the Android context given there
            androidFileProperties() // load properties from assets/koin.properties file
            // module list
            modules(
                listOf(
                    blackForestModule,
                    homeModule,
                    categoryModule,
                    searchModule,
                    cartModule,
                    orderModule
                )
            )
        }
    }
}