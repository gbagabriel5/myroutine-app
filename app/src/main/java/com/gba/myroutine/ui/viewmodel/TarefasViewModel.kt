package com.gba.myroutine.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gba.myroutine.api.Result
import com.gba.myroutine.api.repository.UserRepository
import com.gba.myroutine.constants.TaskConstants
import com.gba.myroutine.room.model.Usuario
import com.gba.myroutine.shared.LoginPreferences
import com.gba.myroutine.valuableobjects.Resource
import kotlinx.coroutines.launch

class TarefasViewModel(
        application: Application,
        val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val sharedPreferences = LoginPreferences(application)

    private val _userWithTasks = MutableLiveData<Resource<Usuario>>()
    val userWithTasks: LiveData<Resource<Usuario>> = _userWithTasks

    private var _userLogout = MutableLiveData<Boolean>()
    val userLogout : LiveData<Boolean> = _userLogout

    private var _userId = MutableLiveData<Boolean>()
    val userId : LiveData<Boolean> = _userId

    fun load() {
        viewModelScope.launch {
            val id = sharedPreferences.get(TaskConstants.SHARED.USER_ID)
            if(!id.isNullOrBlank()) {
                val response = userRepository.getById(id.toInt())
                if (response is Result.Success) {
                    _userWithTasks.value = Resource.success(response.data)
                } else if (response is Result.Error) {
                    _userWithTasks.value = Resource.error(response.exception)
                }
            }
        }
    }

    fun getUserId(): Int {
        val id = sharedPreferences.get(TaskConstants.SHARED.USER_ID)
        _userId.value = true
        return id.toInt()
    }

    fun deslogar() {
        //removendo email do sharedPreferences
        sharedPreferences.remove(TaskConstants.SHARED.USER_ID)
        _userLogout.value = true
    }
}