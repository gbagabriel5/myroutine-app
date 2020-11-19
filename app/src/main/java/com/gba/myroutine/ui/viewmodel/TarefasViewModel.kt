package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.repository.TarefaRepository
import com.gba.myroutine.repository.UsuarioRepository
import com.gba.myroutine.shared.LoginPreferences

class TarefasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TarefaRepository(application.applicationContext)

    private val userRepository = UsuarioRepository(application.applicationContext)

    private val sharedPreferences = LoginPreferences(application)

    private val mTarefaList = MutableLiveData<List<Tarefa>>()
    val tarefaList: LiveData<List<Tarefa>> = mTarefaList

    private var mDeslogarUsuario = MutableLiveData<Boolean>()
    val usuarioDeslogado : LiveData<Boolean> = mDeslogarUsuario

    fun load() {
        //Pegando e-mail do usuario logado
        var email = sharedPreferences.get(TaskConstants.SHARED.USER_EMAIL)
        //Pegando usuario pelo e-mail
        val user = userRepository.getByEmail(email)
        //pegando lista de tarefas pelo pelo id do usuario
        mTarefaList.value = repository.getAllByUserId(user.id)
    }

    fun deslogar() {
        //removendo email do sharedPreferences
        sharedPreferences.remove(TaskConstants.SHARED.USER_EMAIL)
        mDeslogarUsuario.value = true
    }

}