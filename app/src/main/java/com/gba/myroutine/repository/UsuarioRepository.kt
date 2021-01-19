package com.gba.myroutine.repository

import android.content.Context
import com.gba.myroutine.helper.MyRoutineDataBase
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.response.Result

class UsuarioRepository(context: Context) {

    private val dataBase = MyRoutineDataBase.getDataBase(context).usuarioDao()

    fun save(usuario: Usuario) : Boolean = dataBase.save(usuario) > 0

    suspend fun getByLogin(uEmail: String, uSenha: String) =
        try {
            Result.Success(data = dataBase.getByLogin(uEmail, uSenha))
        } catch (exception: Exception) {
            Result.Error(exception)
        }

    fun getByEmail(uEmail: String) =
        try {
            Result.Success(data = dataBase.getByEmail(uEmail))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
}