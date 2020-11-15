package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.repository.UsuarioRepository
import com.gba.myroutine.shared.LoginPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val sharedPreferences = LoginPreferences(application)

    private val repository: UsuarioRepository = UsuarioRepository(context)

    private var mLoginUsuario = MutableLiveData<Usuario>()
    val usuario : LiveData<Usuario> = mLoginUsuario

    private var mUsuarioLogado = MutableLiveData<Boolean>()
    val usuarioLogado : LiveData<Boolean> = mUsuarioLogado

    fun load(usuario: Usuario) {
        mLoginUsuario.value = repository.getByLogin(usuario.email, usuario.senha)
        if(mLoginUsuario.value != null)
            sharedPreferences.store(TaskConstants.SHARED.USER_EMAIL, usuario.email)
    }

    fun verificaUsuarioLogado() {
        val email = sharedPreferences.get(TaskConstants.SHARED.USER_EMAIL)
        if(!email.isNullOrBlank()) {
            val usuario: Usuario? = repository.getByEmail(email)
            usuario?.let { mUsuarioLogado.value = true }
        }
    }
}