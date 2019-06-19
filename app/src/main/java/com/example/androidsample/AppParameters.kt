package com.example.androidsample


object AppParameters {

    const val DATABASE_NAME = "sample_app.db"

//    const val DATASET_FRESH_TIMEOUT = 20L   // TODO testing value
        const val DATASET_FRESH_TIMEOUT = 7200L
//    const val USER_FRESH_TIMEOUT = 10L      // TODO testing value
        const val USER_FRESH_TIMEOUT = 3600L


    const val SYNC_INTERVAL_HOURS = 3

    // TODO change tag
    const val APP_SYNC_TAG = "SampleApp-sync"

}
