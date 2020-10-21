package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.repository.UsuarioRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val repository: UsuarioRepository = UsuarioRepository(context)

    private var mLoginUsuario = MutableLiveData<Usuario>()
    val usuario : LiveData<Usuario> = mLoginUsuario

    fun load(usuario: Usuario) {
        mLoginUsuario.value = repository.getLogin(usuario.email, usuario.senha)
    }
}