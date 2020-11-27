package com.gba.myroutine.api.retrofit

import com.gba.myroutine.api.service.TaskService
import com.gba.myroutine.api.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitClient {
    companion object {
        const val BASE_URL = "http://172.100.10.75:8080/"
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        }
        val taskService: TaskService by lazy {
            retrofit.create(TaskService::class.java)
        }

        val userService: UserService by lazy {
            retrofit.create(UserService::class.java)
        }
    }
}