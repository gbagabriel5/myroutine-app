package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gba.myroutine.api.Result
import com.gba.myroutine.api.repository.UserRepository
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.room.model.Usuario
import com.gba.myroutine.shared.LoginPreferences
import com.gba.myroutine.valuableobjects.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
        application: Application,
        val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val sharedPreferences = LoginPreferences(application)

    private var _Login = MutableLiveData<Resource<Boolean>>()
    val login : LiveData<Resource<Boolean>> = _Login

    private var _User = MutableLiveData<Resource<Usuario>>()
    val user : LiveData<Resource<Usuario>> = _User

    private var _UserId = MutableLiveData<Boolean>()
    val userId : LiveData<Boolean> = _UserId

    private var mUsuarioLogado = MutableLiveData<Resource<Usuario>>()
    val usuarioLogado : LiveData<Resource<Usuario>> = mUsuarioLogado

    fun validateLogin(usuario: Usuario) {
        viewModelScope.launch {
            val response = userRepository.validatesLogin(usuario)
            if (response is Result.Success) {
                _Login.value = Resource.success(true)
                sharedPreferences.store(TaskConstants.SHARED.USER_ID, usuario.id.toString())
            } else if (response is Result.Error) {
                _Login.value = Resource.error(response.exception)
            }
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            val response = userRepository.getByEmail(email)
            if (response is Result.Success) {
                _User.value = Resource.success(response.data)
            } else if (response is Result.Error) {
                _User.value = Resource.error(response.exception)
            }
        }
    }

    fun saveIdToSharedPreferences(id: String) {
        sharedPreferences.store(TaskConstants.SHARED.USER_ID, id)
        _UserId.value = true
    }

    fun verificaUsuarioLogado() {
        viewModelScope.launch {
            val id = sharedPreferences.get(TaskConstants.SHARED.USER_ID)
            if(!id.isNullOrBlank()) {
                val response = userRepository.getById(id.toInt())
                if (response is Result.Success) {
                    mUsuarioLogado.value = Resource.success(response.data)
                } else if (response is Result.Error) {
                    mUsuarioLogado.value = Resource.error(response.exception)
                }
            }
        }
    }
}