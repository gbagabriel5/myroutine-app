package com.gba.myroutine.room.repository

import android.content.Context
import com.gba.myroutine.api.Result
import com.gba.myroutine.room.helper.MyRoutineDataBase
import com.gba.myroutine.room.model.Tarefa
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class TarefaRepository(context: Context) {

    private val dataBase = MyRoutineDataBase.getDataBase(context).tarefaDao()

    suspend fun save(tarefa: Tarefa) = coroutineScope {
        try {
            val response = dataBase.save(tarefa)
            Result.Success(data = response)
        } catch (exception: Exception) {
            Result.Error(Exception())
        }
    }

    suspend fun update(tarefa: Tarefa) = coroutineScope {
        try {
            val response = dataBase.update(tarefa)
            Result.Success(data = response)
        } catch (exception: Exception) {
            Result.Error(Exception())
        }
    }

    fun delete(tarefa: Tarefa) : Boolean = dataBase.delete(tarefa) > 0

    fun get(id: Int) : Tarefa = dataBase.get(id)

    fun getAll(): List<Tarefa> = dataBase.getAll()

    fun getAllByUserId(id: Int) : List<Tarefa> = dataBase.getAllByUserId(id)
}