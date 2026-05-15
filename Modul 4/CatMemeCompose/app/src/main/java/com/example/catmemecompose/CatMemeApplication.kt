package com.example.catmemecompose

import android.app.Application
import timber.log.Timber

class CatMemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}