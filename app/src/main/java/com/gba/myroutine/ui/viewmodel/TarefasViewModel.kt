package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.repository.TarefaRepository
import com.gba.myroutine.repository.UsuarioRepository
import com.gba.myroutine.response.Result
import com.gba.myroutine.shared.LoginPreferences
import com.gba.myroutine.valuableobjects.Resource
import kotlinx.coroutines.launch

class TarefasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TarefaRepository(application.applicationContext)

    private val userRepository = UsuarioRepository(application.applicationContext)

    private val sharedPreferences = LoginPreferences(application)

    private val _tarefaList = MutableLiveData<Resource<List<Tarefa>>>()
    val tarefaList: LiveData<Resource<List<Tarefa>>> = _tarefaList

    private var _deslogarUsuario = MutableLiveData<Boolean>()
    val deslogarUsuario : LiveData<Boolean> = _deslogarUsuario

    private val _userByEmail = MutableLiveData<Resource<Usuario>>()
    val userByEmail: LiveData<Resource<Usuario>> = _userByEmail

    fun load() {
        //Pegando e-mail do usuario logado
        val email = sharedPreferences.get(TaskConstants.SHARED.USER_EMAIL)
        //Pegando usuario pelo e-mail

        var userId = 0
        viewModelScope.launch {
            when(val userResponse = userRepository.getByEmail(email)) {
                is Result.Success -> {
                    _userByEmail.value = Resource.success(userResponse.data)
                    userResponse.data?.let { userId = it.id }
                }
                is Result.Error -> _userByEmail.value = Resource.error(userResponse.exception)
            }
        }
        //pegando lista de tarefas pelo pelo id do usuario
        viewModelScope.launch {
            when(val response = repository.getAllByUserId(userId)) {
                is Result.Success -> _tarefaList.value = Resource.success(data = response.data)
                is Result.Error -> _tarefaList.value = Resource.error(response.exception)
            }
        }
    }

    fun deslogar() {
        //removendo email do sharedPreferences
        sharedPreferences.remove(TaskConstants.SHARED.USER_EMAIL)
        _deslogarUsuario.value = true
    }
}