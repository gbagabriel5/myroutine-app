package com.gba.myroutine.ui.viewmodel

import androidx.lifecycle.*
import com.gba.myroutine.api.Result
import com.gba.myroutine.api.repository.UserRepository
import com.gba.myroutine.room.model.Usuario
import com.gba.myroutine.valuableobjects.Resource
import kotlinx.coroutines.launch

class CadastroViewModel(val userRepository: UserRepository) : ViewModel() {

    private var _UserSaved = MutableLiveData<Resource<Usuario>>()
    val userSaved : LiveData<Resource<Usuario>> = _UserSaved

    fun save(usuario: Usuario) {
        viewModelScope.launch {
            val response = userRepository.createUser(usuario)
            if (response is Result.Success) {
                _UserSaved.value = Resource.success(response.data)
            } else if (response is Result.Error) {
                _UserSaved.value = Resource.error(response.exception)
            }
        }
    }
}