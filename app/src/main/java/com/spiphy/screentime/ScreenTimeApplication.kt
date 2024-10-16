package com.spiphy.screentime

import android.app.Application
import com.spiphy.screentime.data.AppContainer
import com.spiphy.screentime.data.DefaultAppContainer

val writeEnabled: Boolean = false

class ScreenTimeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}