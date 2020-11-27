package com.gba.myroutine.api.service

import com.gba.myroutine.room.model.Tarefa
import retrofit2.Response
import retrofit2.http.*

interface TaskService {
    @POST("task")
    suspend fun createTask(@Body task: Tarefa): Response<Tarefa>

    @PUT("task")
    suspend fun updateTask(@Body task: Tarefa): Response<Tarefa>

    @DELETE("task/{id}")
    suspend fun delete(
            @Path("id") id: Int
    ) : Response<Boolean>

    @GET("task/{id}")
    suspend fun getById(
            @Path("id") id: Int
    ) : Response<Tarefa>
}