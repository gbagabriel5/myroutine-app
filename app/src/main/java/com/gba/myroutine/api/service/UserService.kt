package com.gba.myroutine.api.service

import com.gba.myroutine.room.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("user")
    suspend fun createUser(@Body usuario: Usuario) : Response<Usuario>

    @GET("user/validatesLogin")
    suspend fun validatesLogin(
            @Query("email") email: String,
            @Query("password") password: String
    ) : Response<Boolean>

    @GET("user/getByEmail")
    suspend fun getByEmail(
            @Query("email") email: String
    ) : Response<Usuario>

    @GET("user/{id}")
    suspend fun getById(
            @Path("id") id: Int
    ) : Response<Usuario>
}