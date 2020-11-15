package com.gba.myroutine.repository

import android.content.Context
import com.gba.myroutine.helper.MyRoutineDataBase
import com.gba.myroutine.model.Usuario

class UsuarioRepository(context: Context) {

    private val dataBase = MyRoutineDataBase.getDataBase(context).usuarioDao()

    fun save(usuario: Usuario) : Boolean = dataBase.save(usuario) > 0

    fun getByLogin(uEmail: String, uSenha: String) : Usuario = dataBase.getByLogin(uEmail, uSenha)

    fun getByEmail(uEmail: String) : Usuario = dataBase.getByEmail(uEmail)
}