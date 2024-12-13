package com.example.skindisease

import android.app.Application
import com.example.skindisease.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            // declare used Android context
            androidContext(this@KoinApp)
            // declare modules
            modules(listOf(viewModelModule, ktorModule, repositoryModule, useCaseModule,databaseModule))
        }
    }
}