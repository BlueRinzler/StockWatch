package com.sambarnett.stockwatch.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class CompanyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CompanyApplication)
            modules(apiModule, dataBaseModule, repoModule, csvParser, viewModels)

        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}