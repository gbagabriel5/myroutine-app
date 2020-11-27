package com.gba.myroutine.api.repository

import com.gba.myroutine.api.service.TaskService
import com.gba.myroutine.room.model.Tarefa
import kotlinx.coroutines.coroutineScope
import com.gba.myroutine.api.Result
import java.lang.Exception

class TaskRepository(private val taskService: TaskService) {
    suspend fun createTask(task: Tarefa) = coroutineScope {
        try {
            val response = taskService.createTask(task)
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

    suspend fun updateTask(task: Tarefa) = coroutineScope {
        try {
            val response = taskService.updateTask(task)
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

    suspend fun delete(id: Int) = coroutineScope {
        try {
            val response = taskService.delete(id)
            if(response.isSuccessful) {
                if(response.body() != null) {
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

    suspend fun getById(id: Int) = coroutineScope {
        try {
            val response = taskService.getById(id)
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
}