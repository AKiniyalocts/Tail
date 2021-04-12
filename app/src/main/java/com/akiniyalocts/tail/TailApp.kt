package com.akiniyalocts.tail

import android.app.Application
import com.akiniyalocts.tail.di.all
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TailApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TailApp)
            modules(all)
        }
    }
}