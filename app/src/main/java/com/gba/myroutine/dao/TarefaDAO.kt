package com.gba.myroutine.dao

import androidx.room.*
import com.gba.myroutine.model.Tarefa

@Dao
interface TarefaDAO {
    @Insert
    suspend fun save(tarefa: Tarefa) : Long

    @Update
    suspend fun update(tarefa: Tarefa) : Int

    @Delete
    suspend fun delete(tarefa: Tarefa) : Int

    @Query("SELECT *FROM Tarefa WHERE id =:id")
    suspend fun get(id: Int) : Tarefa

    @Query("SELECT *FROM Tarefa")
    suspend fun getAll() : List<Tarefa>

    @Query("SELECT *FROM Tarefa WHERE usuarioId=:uId")
    suspend fun getAllByUserId(uId: Int) : List<Tarefa>
}