package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gba.myroutine.api.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val application: Application,
                            private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(application, repository) as T
    }
}