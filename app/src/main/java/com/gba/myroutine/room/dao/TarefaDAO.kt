package com.gba.myroutine.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.gba.myroutine.room.model.Tarefa

@Dao
interface TarefaDAO {
    @Insert(onConflict = REPLACE)
    suspend fun save(tarefa: Tarefa) : Long

    @Update(onConflict = REPLACE)
    suspend fun update(tarefa: Tarefa) : Int

    @Delete
    fun delete(tarefa: Tarefa) : Int

    @Query("SELECT *FROM Tarefa WHERE id =:id")
    fun get(id: Int) : Tarefa

    @Query("SELECT *FROM Tarefa")
    fun getAll() : List<Tarefa>

    @Query("SELECT *FROM Tarefa WHERE usuarioId=:uId")
    fun getAllByUserId(uId: Int) : List<Tarefa>

}