package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.model.Tarefa
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

    private val mTarefaList = MutableLiveData<Resource<List<Tarefa>>>()
    val tarefaList: LiveData<Resource<List<Tarefa>>> = mTarefaList

    private var mDeslogarUsuario = MutableLiveData<Boolean>()
    val usuarioDeslogado : LiveData<Boolean> = mDeslogarUsuario

    fun load() {
        //Pegando e-mail do usuario logado
        var email = sharedPreferences.get(TaskConstants.SHARED.USER_EMAIL)
        //Pegando usuario pelo e-mail
        val user = userRepository.getByEmail(email)
        //pegando lista de tarefas pelo pelo id do usuario
        viewModelScope.launch {
            when(val response = repository.getAllByUserId(user.id)) {
                is Result.Success -> mTarefaList.value = Resource.success(data = response.data)
                is Result.Error -> mTarefaList.value = Resource.error(response.exception)
            }
        }
    }

    fun deslogar() {
        //removendo email do sharedPreferences
        sharedPreferences.remove(TaskConstants.SHARED.USER_EMAIL)
        mDeslogarUsuario.value = true
    }
}