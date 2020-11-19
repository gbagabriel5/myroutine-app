package com.gba.myroutine.dao

import androidx.room.*
import com.gba.myroutine.model.Tarefa

@Dao
interface TarefaDAO {
    @Insert
    fun save(tarefa: Tarefa) : Long

    @Update
    fun update(tarefa: Tarefa) : Int

    @Delete
    fun delete(tarefa: Tarefa) : Int

    @Query("SELECT *FROM Tarefa WHERE id =:id")
    fun get(id: Int) : Tarefa

    @Query("SELECT *FROM Tarefa")
    fun getAll() : List<Tarefa>

    @Query("SELECT *FROM Tarefa WHERE usuarioId=:uId")
    fun getAllByUserId(uId: Int) : List<Tarefa>

}