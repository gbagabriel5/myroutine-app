package com.gba.myroutine.repository

import android.content.Context
import com.gba.myroutine.helper.MyRoutineDataBase
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.response.Result

class TarefaRepository(context: Context) {

    private val dataBase = MyRoutineDataBase.getDataBase(context).tarefaDao()

    suspend fun save(tarefa: Tarefa) =
        try {
            dataBase.save(tarefa)
            Result.Success(true)
        } catch (exception: java.lang.Exception) {
            Result.Error(java.lang.Exception())
        }

    suspend fun update(tarefa: Tarefa) =
        try {
            dataBase.update(tarefa)
            Result.Success(true)
        } catch (exception: java.lang.Exception) {
            Result.Error(java.lang.Exception())
        }

    suspend fun delete(tarefa: Tarefa) =
        try {
            dataBase.delete(tarefa)
            Result.Success(true)
        } catch (exception: java.lang.Exception) {
            Result.Error(java.lang.Exception())
        }

    suspend fun get(id: Int) =
        try {
            Result.Success(data = dataBase.get(id))
        } catch (exception: java.lang.Exception) {
            Result.Error(java.lang.Exception())
        }

    suspend fun getAll() =
        try {
            Result.Success(data = dataBase.getAll())
        } catch (exception: java.lang.Exception) {
            Result.Error(java.lang.Exception())
        }

    suspend fun getAllByUserId(id: Int) =
        try {
            Result.Success(data = dataBase.getAllByUserId(id))
        } catch (exception: java.lang.Exception) {
            Result.Error(java.lang.Exception())
        }
}