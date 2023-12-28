package com.example.suitmediaapk.api

import com.example.suitmediaapk.dataclass.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("api/users?page=1&per_page=10")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UserResponse>
}