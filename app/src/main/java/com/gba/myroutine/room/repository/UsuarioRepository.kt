package com.gba.myroutine.room.repository

import android.content.Context
import com.gba.myroutine.room.helper.MyRoutineDataBase
import com.gba.myroutine.room.model.Usuario

class UsuarioRepository(context: Context) {

    private val dataBase = MyRoutineDataBase.getDataBase(context).usuarioDao()

    fun save(usuario: Usuario) : Boolean = dataBase.save(usuario) > 0

    fun getByLogin(uEmail: String, uSenha: String) : Usuario = dataBase.getByLogin(uEmail, uSenha)

    fun getByEmail(uEmail: String) : Usuario = dataBase.getByEmail(uEmail)
}