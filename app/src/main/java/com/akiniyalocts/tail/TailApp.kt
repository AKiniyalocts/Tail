package com.akiniyalocts.tail

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TailApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}