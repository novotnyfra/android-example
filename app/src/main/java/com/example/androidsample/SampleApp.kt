package com.example.androidsample

import android.app.Application
import com.example.androidsample.di.appModule
import com.example.androidsample.di.dataModule
import com.example.androidsample.di.netModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, dataModule, netModule))

        Timber.plant(Timber.DebugTree())

    }
}
