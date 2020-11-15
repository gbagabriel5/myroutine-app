package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.repository.TarefaRepository
import com.gba.myroutine.shared.LoginPreferences

class TarefasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TarefaRepository(application.applicationContext)

    private val sharedPreferences = LoginPreferences(application)


    private val mTarefaList = MutableLiveData<List<Tarefa>>()
    val tarefaList: LiveData<List<Tarefa>> = mTarefaList

    private var mDeslogarUsuario = MutableLiveData<Boolean>()
    val usuarioDeslogado : LiveData<Boolean> = mDeslogarUsuario

    fun load() {
        mTarefaList.value = repository.getAll()
    }

    fun deslogar() {
        sharedPreferences.remove(TaskConstants.SHARED.USER_EMAIL)
        mDeslogarUsuario.value = true
    }

}