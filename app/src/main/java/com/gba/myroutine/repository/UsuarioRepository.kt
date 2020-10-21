package com.gba.myroutine.repository

import android.content.Context
import com.gba.myroutine.helper.UsuarioDataBase
import com.gba.myroutine.model.Usuario

class UsuarioRepository(context: Context) {

    private val dataBase = UsuarioDataBase.getDataBase(context).usuarioDao()

    fun save(usuario: Usuario) : Boolean = dataBase.save(usuario) > 0

    fun getLogin(uEmail: String, uSenha: String) : Usuario = dataBase.getLogin(uEmail, uSenha)
}