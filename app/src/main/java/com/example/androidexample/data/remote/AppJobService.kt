package com.example.androidexample.data.remote

import com.example.androidexample.data.AppRepository
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import org.koin.android.ext.android.inject
import timber.log.Timber

class AppJobService : JobService() {

    private val repository by inject<AppRepository>()

    override fun onStartJob(job: JobParameters?): Boolean {

        Timber.d("job running")
        
        // refresh data
        // TODO

        return true    // Answers the question: "Is there still work going on?"
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return true    // Answers the question: "Should this job be retried?"
    }

}
