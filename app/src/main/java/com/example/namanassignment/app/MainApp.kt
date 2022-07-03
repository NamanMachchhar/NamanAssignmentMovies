package com.example.namanassignment.app

import android.app.Application
import com.example.namanassignment.BuildConfig
import com.example.namanassignment.di.appModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApp)
            modules(listOf(appModule))
        }
    }
}