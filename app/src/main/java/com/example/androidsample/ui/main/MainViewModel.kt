package com.example.androidsample.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.example.androidsample.data.AppRepository
import com.example.androidsample.data.model.User
import com.example.androidsample.data.resource.Resource
import com.example.androidsample.util.livedata.LiveEvent
import com.example.androidsample.util.livedata.event
import timber.log.Timber

class MainViewModel(application: Application, private val repository: AppRepository) : AndroidViewModel(application) {

    companion object {
        const val ERROR_API = "ERROR_API"
    }

    val loading = MutableLiveData<Boolean>()
    val errorEvent = MutableLiveData<LiveEvent<String>>()


    // list of users

    private val users = MutableLiveData<List<User>>()

    fun getUsers(owner: LifecycleOwner) : LiveData<List<User>> {
        loadUsers(owner)
        return users
    }

    private var usersResource: LiveData<Resource<List<User>>>? = null

    private fun loadUsers(owner: LifecycleOwner) {
        // remove previous observers, should be possible with LiveData Mediator also
        usersResource?.removeObservers(owner)
        usersResource = repository.loadUsers(false)
        usersResource?.observe(owner, Observer {resource ->
            observeUsersResource(resource)
        })
    }

    fun reloadUsers() {
        val refreshLiveData = repository.loadUsers(true)
        refreshLiveData.observeForever(object : Observer<Resource<List<User>>> {
            override fun onChanged(resource: Resource<List<User>>?) {
                observeUsersResource(resource)
                if (resource?.status != Resource.LOADING) {
                    refreshLiveData.removeObserver(this)
                }
            }
        })
    }

    private fun observeUsersResource(resource: Resource<List<User>>?) {
        when (resource?.status) {
            Resource.SUCCESS -> {
                Timber.d("Resource SUCCESS")
                users.value = resource.data
                loading.value = false
            }
            Resource.ERROR -> {
                Timber.d("Resource ERROR")
                Timber.d("error, data = ${resource.data?.size}, network error: ${resource.message}")

                errorEvent.event(ERROR_API)
                loading.value = false
            }
            Resource.LOADING -> {
                Timber.d("Resource LOADING")
                users.value = resource.data
                loading.value = true
            }
        }
    }

    fun nukeUsers(@Suppress("UNUSED_PARAMETER") view: View) {
        repository.deleteAllUsers()
    }


    // selected (single) user - user detail

    var selectedUser = MutableLiveData<User>()

    fun selectUser(owner: LifecycleOwner, userId: Long) {
        Timber.d("setting id: $userId")
        loadUser(owner, userId)
    }

    private var userResource: LiveData<Resource<User>>? = null

    private fun loadUser(owner: LifecycleOwner, userId: Long) {
        userResource?.removeObservers(owner)
        userResource = repository.loadUser(userId)
        userResource?.observe(owner, Observer { resource ->
            observeUserResource(resource)
        })
    }

    fun reloadUser() {
        selectedUser.value?.id?.let { userId ->
            val refreshLiveData = repository.loadUser(userId, true)
            refreshLiveData.observeForever(object : Observer<Resource<User>> {
                override fun onChanged(resource: Resource<User>?) {
                    observeUserResource(resource)
                    if (resource?.status != Resource.LOADING) {
                        refreshLiveData.removeObserver(this)
                    }
                }
            })
        }
    }

    private fun observeUserResource(resource: Resource<User>?) {
        when (resource?.status) {
            Resource.SUCCESS -> {
                Timber.d("Resource SUCCESS")
                selectedUser.value = resource.data
                loading.value = false
            }
            Resource.ERROR -> {
                Timber.d("Resource ERROR")
                Timber.d("error, data = ${resource.data?.name}, network error: ${resource.message}")

                errorEvent.event(ERROR_API)
                loading.value = false
            }
            Resource.LOADING -> {
                Timber.d("Resource LOADING")
                selectedUser.value = resource.data
                loading.value = true
            }
        }
    }

}
