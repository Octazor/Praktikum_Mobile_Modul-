package com.example.modulmobilecat

import android.app.Application
import timber.log.Timber

class CatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("CatApplication dibuat, Timber siap dipakai")
    }
}
