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

class CadastroTarefaViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val sharedPreferences = LoginPreferences(application)

    private val repository: TarefaRepository = TarefaRepository(context)

    private val userRepository = UsuarioRepository(application.applicationContext)

    private var mTarefaSalva = MutableLiveData<Resource<Boolean>>()
    val tarefaSalva : LiveData<Resource<Boolean>> = mTarefaSalva

    private var mTarefaRemovida = MutableLiveData<Resource<Boolean>>()
    val tarefaRemovida : LiveData<Resource<Boolean>> = mTarefaRemovida

    private var mTarefa = MutableLiveData<Resource<Tarefa>>()
    val tarefa : LiveData<Resource<Tarefa>> = mTarefa

    private val mUsuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = mUsuario

    fun saveOrUpdate(tarefa: Tarefa) {
        viewModelScope.launch {
            mTarefaSalva.value = Resource.loading()
            when(tarefa.id) {
                0 -> {
                    when(val responseSave = repository.save(tarefa)) {
                        is Result.Success -> mTarefaSalva.value = Resource.success(true)
                        is Result.Error -> mTarefaSalva.value = Resource.error(responseSave.exception)
                    }
                } else -> {
                    when(val responseUpdate = repository.update(tarefa)) {
                        is Result.Success -> mTarefaSalva.value = Resource.success(true)
                        is Result.Error -> mTarefaSalva.value =
                            Resource.error(responseUpdate.exception)
                    }
                }
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            mTarefaRemovida.value = Resource.loading()
            when(val response = repository.delete(Tarefa().apply {this.id = id})) {
                is Result.Success -> mTarefaRemovida.value = Resource.success(true)
                is Result.Error -> mTarefaRemovida.value = Resource.error(response.exception)
            }
        }
    }

    fun load(id: Int) {
        viewModelScope.launch {
            mTarefa.value = Resource.loading()
            when(val response = repository.get(id)) {
                is Result.Success -> mTarefa.value = Resource.success(response.data)
                is Result.Error -> mTarefa.value = Resource.error(response.exception)
            }
        }
    }

    fun getUser(): Usuario {
        val email = sharedPreferences.get(TaskConstants.SHARED.USER_EMAIL)
        val user = userRepository.getByEmail(email)
        mUsuario.value = user
        return user
    }
}