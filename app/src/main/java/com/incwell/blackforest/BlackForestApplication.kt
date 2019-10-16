package com.incwell.blackforest

import android.app.Application
import com.incwell.blackforest.module.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BlackForestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger() // use AndroidLogger as Koin Logger - default Level.INFO
            androidContext(this@BlackForestApplication) // use the Android context given there
            androidFileProperties() // load properties from assets/koin.properties file
            // module list
            modules(
                listOf(
                    homeModule
                )
            )
        }
    }
}