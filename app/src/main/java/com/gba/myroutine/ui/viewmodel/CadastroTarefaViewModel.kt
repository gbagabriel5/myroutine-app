package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gba.myroutine.api.Result
import com.gba.myroutine.api.repository.TaskRepository
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.room.model.Tarefa
import com.gba.myroutine.room.model.Usuario
import com.gba.myroutine.room.repository.TarefaRepository
import com.gba.myroutine.shared.LoginPreferences
import com.gba.myroutine.valuableobjects.Resource
import kotlinx.coroutines.launch

class CadastroTarefaViewModel(
        application: Application,
        val taskRepository: TaskRepository
) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val sharedPreferences = LoginPreferences(application)

    private val repository: TarefaRepository = TarefaRepository(context)

    private var _SavedTask = MutableLiveData<Resource<Tarefa>>()
    val savedTask: LiveData<Resource<Tarefa>> = _SavedTask

    private var mTarefaSalva = MutableLiveData<Resource<Boolean>>()
    val tarefaSalva: LiveData<Resource<Boolean>> = mTarefaSalva

    private var mTarefaRemovida = MutableLiveData<Resource<Boolean>>()
    val tarefaRemovida: LiveData<Resource<Boolean>> = mTarefaRemovida

    private var mTarefa = MutableLiveData<Resource<Tarefa>>()
    val tarefa: LiveData<Resource<Tarefa>> = mTarefa

    private val mUsuario = MutableLiveData<Usuario>()
    val usuario: LiveData<Usuario> = mUsuario

    fun saveOrUpdate(tarefa: Tarefa) {
        val userId = sharedPreferences.get(TaskConstants.SHARED.USER_ID)
        tarefa.user.id = userId.toInt()
        tarefa.usuarioId = userId.toInt()
        if (tarefa.id == 0) {
            viewModelScope.launch {
                val response = taskRepository.createTask(tarefa)
                if (response is Result.Success)
                    _SavedTask.value = Resource.success(response.data)
                else if (response is Result.Error)
                    _SavedTask.value = Resource.error(response.exception)
            }
        } else {
            viewModelScope.launch {
                val response = taskRepository.updateTask(tarefa)
                if (response is Result.Success)
                    _SavedTask.value = Resource.success(response.data)
                else if (response is Result.Error)
                    _SavedTask.value = Resource.error(response.exception)
            }
        }
    }

    fun saveOrUpdateRoom(tarefa: Tarefa) {
        if(tarefa.id == 0) {
            viewModelScope.launch {
                val response =  repository.save(tarefa)
                if (response is Result.Success)
                    mTarefaSalva.value = Resource.success(true)
                else if (response is Result.Error)
                    mTarefaRemovida.value = Resource.error(response.exception)
            }
        } else {
            viewModelScope.launch {
                val response =  repository.update(tarefa)
                if (response is Result.Success)
                    mTarefaSalva.value = Resource.success(true)
                else if (response is Result.Error)
                    mTarefaRemovida.value = Resource.error(response.exception)
            }
        }
    }

    fun delete(id: Int) {
        if(id > 0) {
            viewModelScope.launch {
                val response = taskRepository.delete(id)
                if (response is Result.Success)
                    mTarefaRemovida.value = Resource.success(true)
                else if (response is Result.Error)
                    mTarefaRemovida.value = Resource.error(response.exception)
            }
        }
    }

    fun load(id: Int) {
        if(id > 0) {
            viewModelScope.launch {
                val response = taskRepository.getById(id)
                if (response is Result.Success)
                    mTarefa.value = Resource.success(response.data)
                else if (response is Result.Error)
                    mTarefa.value = Resource.error(response.exception)
            }
        }
    }
}