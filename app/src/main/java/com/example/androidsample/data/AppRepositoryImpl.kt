package com.example.androidsample.data

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.androidsample.AppParameters
import com.example.androidsample.data.local.SharedPrefRepo
import com.example.androidsample.data.local.UserDao
import com.example.androidsample.data.model.User
import com.example.androidsample.data.remote.AppJobService
import com.example.androidsample.data.remote.UserApi
import com.example.androidsample.data.resource.ApiResponse
import com.example.androidsample.data.resource.NetworkBoundResource
import com.example.androidsample.data.resource.Resource
import com.firebase.jobdispatcher.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


class AppRepositoryImpl(
    private val context: Context,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val sharedPrefRepo: SharedPrefRepo
) : AppRepository {

    init {
        scheduleRecurringFetchUserSync()
    }

    override fun loadUsers(forceFetch: Boolean): LiveData<Resource<List<User>>>
            = object : NetworkBoundResource<List<User>, List<User>>() {
        override fun saveCallResult(item: List<User>) {
            userDao.replaceAll(item)
            sharedPrefRepo.lastDataSetFetch = System.currentTimeMillis() / 1000
        }
        override fun shouldFetch(data: List<User>?): Boolean
                = forceFetch || (System.currentTimeMillis() / 1000 - sharedPrefRepo.lastDataSetFetch > AppParameters.DATASET_FRESH_TIMEOUT)
        override fun loadFromDb(): LiveData<List<User>>
                = userDao.getAll()
        override fun createCall(): LiveData<ApiResponse<List<User>>>
                = userApi.getUsers()
    }.asLiveData()

    override fun loadUser(userId: Long, forceFetch: Boolean): LiveData<Resource<User>>
            = object : NetworkBoundResource<User, User>() {
        override fun saveCallResult(item: User)
                = userDao.insertOrUpdate(item)
        override fun shouldFetch(data: User?): Boolean
                = forceFetch || (data?.isFresh(AppParameters.USER_FRESH_TIMEOUT) == false)
        override fun loadFromDb(): LiveData<User>
                = userDao.getById(userId)
        override fun createCall(): LiveData<ApiResponse<User>>
                = userApi.getUser(userId)
    }.asLiveData()

    override fun deleteAllUsers() {
        AsyncTask.execute { userDao.deleteAll() }
        Timber.d("users nuked")
    }

    override fun scheduleRecurringFetchUserSync() {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))

        // Create the Job to periodically sync data
        val syncDataJob = dispatcher.newJobBuilder()
            .setService(AppJobService::class.java)
            .setTag(AppParameters.APP_SYNC_TAG)
            /*
             * Network constraints on which this Job should run. We choose to run on any
             * network, but you can also choose to run only on un-metered networks or when the
             * device is charging. It might be a good idea to include a preference for this,
             * as some users may not want to download any data on their mobile plan. ($$$)
             */
//                .setConstraints(Constraint.ON_ANY_NETWORK)
            .setConstraints(Constraint.ON_UNMETERED_NETWORK)
            .setConstraints(Constraint.DEVICE_CHARGING)
            .setLifetime(Lifetime.FOREVER)
            .setRecurring(true)
            /*
             * We want the weather data to be synced every 3 to 4 hours. The first argument for
             * Trigger's static executionWindow method is the start of the time frame when the
             * sync should be performed. The second argument is the latest point in time at
             * which the data should be synced. Please note that this end time is not
             * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
             */
            .setTrigger(
                Trigger.executionWindow(
                    SYNC_INTERVAL_SECONDS,
                    SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
            /*
             * If a Job with the tag with provided already exists, this new job will replace
             * the old one.
             */
            .setReplaceCurrent(true)
            .build()

        // Schedule the Job with the dispatcher
        val result = dispatcher.schedule(syncDataJob)
        Timber.d("sync job scheduled (result: $result)")
    }

    companion object {
        // have to be >15 minutes until API 24
        private val SYNC_INTERVAL_SECONDS = TimeUnit.HOURS.toSeconds(AppParameters.SYNC_INTERVAL_HOURS.toLong()).toInt()
        private val SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3
    }

}
