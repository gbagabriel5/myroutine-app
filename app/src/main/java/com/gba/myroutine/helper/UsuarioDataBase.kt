package com.gba.myroutine.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gba.myroutine.dao.UsuarioDAO
import com.gba.myroutine.model.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class UsuarioDataBase : RoomDatabase() {

    abstract fun usuarioDao() : UsuarioDAO

    companion object {

        private lateinit var INSTANCE : UsuarioDataBase

        fun getDataBase(context: Context) : UsuarioDataBase {
            if(!::INSTANCE.isInitialized) {
                synchronized(UsuarioDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        UsuarioDataBase::class.java,
                        "myRoutineDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}