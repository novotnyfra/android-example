package com.example.androidexample.data

import android.content.Context
import com.example.androidexample.AppParameters
import java.util.concurrent.TimeUnit


class AppRepositoryImpl(val context: Context) : AppRepository {



    companion object {
        // have to be >15 minutes until API 24
        private val SYNC_INTERVAL_SECONDS = TimeUnit.HOURS.toSeconds(AppParameters.SYNC_INTERVAL_HOURS.toLong()).toInt()
        private val SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3
    }

}
