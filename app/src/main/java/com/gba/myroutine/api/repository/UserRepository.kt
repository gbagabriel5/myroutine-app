package com.gba.myroutine.api.repository

import kotlinx.coroutines.coroutineScope
import com.gba.myroutine.api.Result
import com.gba.myroutine.api.service.UserService
import com.gba.myroutine.room.model.Usuario
import java.lang.Exception

class UserRepository(private val userService: UserService) {
    suspend fun createUser(usuario: Usuario) = coroutineScope {
        try {
            val response = userService.createUser(usuario)
            if(response.isSuccessful) {
                if(response.body() != null) {
                    Result.Success(data = response.body())
                } else {
                    Result.Error(Exception())
                }
            } else {
                Result.Error(Exception())
            }
        } catch (exception: Exception) {
            Result.Error(Exception())
        }
    }

    suspend fun getById(id: Int) = coroutineScope {
        try {
            val response = userService.getById(id)
            if(response.isSuccessful) {
                if(response.body() != null) {
                    Result.Success(data = response.body())
                } else {
                    Result.Error(Exception())
                }
            } else {
                Result.Error(Exception())
            }
        } catch (exception: Exception) {
            Result.Error(Exception())
        }
    }

    suspend fun getByEmail(email: String) = coroutineScope {
        try {
            val response = userService.getByEmail(email)
            if(response.isSuccessful) {
                if(response.body() != null) {
                    Result.Success(data = response.body())
                } else {
                    Result.Error(Exception())
                }
            } else {
                Result.Error(Exception())
            }
        } catch (exception: Exception) {
            Result.Error(Exception())
        }
    }

    suspend fun validatesLogin(usuario: Usuario) = coroutineScope {
        try {
            val response = userService.validatesLogin(
                    email = usuario.email,
                    password = usuario.senha
            )
            if(response.isSuccessful) {
                if(response.body() == true) {
                    Result.Success(response.body())
                } else {
                    Result.Error(Exception())
                }
            } else {
                Result.Error(Exception())
            }
        } catch (exception: Exception) {
            Result.Error(Exception())
        }
    }
}