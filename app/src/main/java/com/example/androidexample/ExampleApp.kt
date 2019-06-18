package com.example.androidexample

import android.app.Application
import com.example.androidexample.di.appModule
import com.example.androidexample.di.dataModule
import com.example.androidexample.di.netModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class ExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, dataModule, netModule))

        Timber.plant(Timber.DebugTree())

    }

}
