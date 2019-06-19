package com.example.androidsample.data

import androidx.lifecycle.LiveData
import com.example.androidsample.data.model.User
import com.example.androidsample.data.resource.Resource


interface AppRepository {

    fun loadUsers(forceFetch: Boolean = false) : LiveData<Resource<List<User>>>
    fun loadUser(userId: Long, forceFetch: Boolean = false) : LiveData<Resource<User>>
    fun deleteAllUsers()
    fun scheduleRecurringFetchUserSync()

}
