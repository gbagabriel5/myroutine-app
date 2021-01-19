package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.repository.UsuarioRepository
import com.gba.myroutine.response.Result
import com.gba.myroutine.shared.LoginPreferences
import com.gba.myroutine.valuableobjects.Resource
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val sharedPreferences = LoginPreferences(application)

    private val repository: UsuarioRepository = UsuarioRepository(context)

    private var mLoginUsuario = MutableLiveData<Resource<Usuario>>()
    val usuario : LiveData<Resource<Usuario>> = mLoginUsuario

    private var mUsuarioLogado = MutableLiveData<Resource<Boolean>>()
    val usuarioLogado : LiveData<Resource<Boolean>> = mUsuarioLogado

    fun load(usuario: Usuario) {
        mLoginUsuario.value = Resource.loading()
            viewModelScope.launch {
            when(val response = repository.getByLogin(usuario.email, usuario.senha)) {
                is Result.Success -> {
                    mLoginUsuario.value = Resource.success(data = response.data)
                    sharedPreferences.store(TaskConstants.SHARED.USER_EMAIL, usuario.email)
                }
                is Result.Error -> mLoginUsuario.value = Resource.error(response.exception)
            }
        }
    }

    fun verificaUsuarioLogado() {
        val email = sharedPreferences.get(TaskConstants.SHARED.USER_EMAIL)
        if(email.isNotEmpty()) {
            viewModelScope.launch {
                when(val response = repository.getByEmail(email)) {
                    is Result.Success -> {
                        response.data?.let { mUsuarioLogado.value = Resource.success(true) }
                    }
                    is Result.Error -> mUsuarioLogado.value = Resource.error(response.exception)
                }
            }
        }
    }

    fun setUndefinedOnLogin() {
        mLoginUsuario.value = Resource.undefined()
    }
}