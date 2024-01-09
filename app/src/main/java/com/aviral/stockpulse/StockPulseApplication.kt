package com.aviral.stockpulse

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StockPulseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}