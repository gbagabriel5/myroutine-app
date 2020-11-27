package com.gba.myroutine.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gba.myroutine.api.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class CadastroViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CadastroViewModel(repository) as T
    }
}