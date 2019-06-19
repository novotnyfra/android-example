package com.example.androidsample.data.remote

import androidx.lifecycle.Observer
import com.example.androidsample.data.AppRepository
import com.example.androidsample.data.model.User
import com.example.androidsample.data.resource.Resource
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import org.koin.android.ext.android.inject
import timber.log.Timber

class AppJobService : JobService() {

    private val repository by inject<AppRepository>()

    override fun onStartJob(job: JobParameters?): Boolean {

        Timber.d("job running")

        // refresh data
        val refreshLiveData = repository.loadUsers(true)
        refreshLiveData.observeForever(object : Observer<Resource<List<User>>> {
            override fun onChanged(resource: Resource<List<User>>?) {
                when (resource?.status) {
                    Resource.SUCCESS -> {
                        Timber.d("Resource SUCCESS")
                    }
                    Resource.ERROR -> {
                        Timber.w("Resource ERROR")
                        Timber.d("error, data = ${resource.data?.size}, network error: ${resource.message}")
                    }
                    Resource.LOADING -> {
                        Timber.d("Resource LOADING")
                    }
                }
                if (resource?.status != Resource.LOADING) {
                    refreshLiveData.removeObserver(this)
                }
            }
        })

        return true    // Answers the question: "Is there still work going on?"
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return true    // Answers the question: "Should this job be retried?"
    }

}
