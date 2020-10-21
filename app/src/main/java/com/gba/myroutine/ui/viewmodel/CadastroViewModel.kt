package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gba.myroutine.model.Usuario
import com.gba.myroutine.repository.UsuarioRepository

class CadastroViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val repository: UsuarioRepository = UsuarioRepository(context)

    private var mSaveUsuario = MutableLiveData<Boolean>()
    val saveUsuario : LiveData<Boolean> = mSaveUsuario

    fun save(guest: Usuario) {
        mSaveUsuario.value = repository.save(guest)
    }
}