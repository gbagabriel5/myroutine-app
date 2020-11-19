package com.gba.myroutine.repository

import android.content.Context
import com.gba.myroutine.helper.MyRoutineDataBase
import com.gba.myroutine.model.Tarefa

class TarefaRepository(context: Context) {

    private val dataBase = MyRoutineDataBase.getDataBase(context).tarefaDao()

    fun save(tarefa: Tarefa) : Boolean = dataBase.save(tarefa) > 0

    fun update(tarefa: Tarefa) : Boolean = dataBase.update(tarefa) > 0

    fun delete(tarefa: Tarefa) : Boolean = dataBase.delete(tarefa) > 0

    fun get(id: Int) : Tarefa = dataBase.get(id)

    fun getAll(): List<Tarefa> = dataBase.getAll()

    fun getAllByUserId(id: Int) : List<Tarefa> = dataBase.getAllByUserId(id)
}