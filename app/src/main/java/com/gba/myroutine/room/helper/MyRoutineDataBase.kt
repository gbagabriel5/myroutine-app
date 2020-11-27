package com.gba.myroutine.room.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gba.myroutine.room.dao.TarefaDAO
import com.gba.myroutine.room.dao.UsuarioDAO
import com.gba.myroutine.room.model.Tarefa
import com.gba.myroutine.room.model.Usuario

@Database(entities = [Usuario::class, Tarefa::class], version = 2)
abstract class MyRoutineDataBase : RoomDatabase() {

    abstract fun usuarioDao() : UsuarioDAO

    abstract fun tarefaDao() : TarefaDAO

    companion object {

        private lateinit var INSTANCE : MyRoutineDataBase

        fun getDataBase(context: Context) : MyRoutineDataBase {
            if(!::INSTANCE.isInitialized) {
                synchronized(MyRoutineDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MyRoutineDataBase::class.java,
                        "myRoutineDB"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}