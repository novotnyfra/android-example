package com.example.androidsample.data.remote

import androidx.lifecycle.LiveData
import com.example.androidsample.data.model.User
import com.example.androidsample.data.resource.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users")
    fun getUsers(): LiveData<ApiResponse<List<User>>>

    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Long): LiveData<ApiResponse<User>>

}
