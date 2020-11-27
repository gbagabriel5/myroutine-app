package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gba.myroutine.api.repository.TaskRepository

@Suppress("UNCHECKED_CAST")
class CadastroTarefaViewModelFactory(private val application: Application,
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CadastroTarefaViewModel(application, repository) as T
    }
}