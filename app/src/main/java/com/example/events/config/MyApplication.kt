package com.example.events.config

import android.app.Application
import com.example.events.data.di.dataModule
import com.example.events.domain.di.domainModule
import com.example.events.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    /**
     * Base Application class for the app
     * Start koin for dependency injection
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            modules(viewModelModule, dataModule, domainModule)
        }
    }
}