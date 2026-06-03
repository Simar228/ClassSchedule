package com.example.classschedule.Presentation.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Data.dao.UserDao
import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.repository.AuthRepository
import com.example.classschedule.Domain.usecase.room.AddUserUseCase
import com.example.classschedule.Domain.usecase.room.GetUserUseCase
import com.example.classschedule.Domain.usecase.room.RemoveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    val removeUserUseCase: RemoveUserUseCase,
    val getUserUseCase: GetUserUseCase,
    val authRepository: AuthRepository,

) : ViewModel(){

    fun logOut(){
        viewModelScope.launch {
            removeUserUseCase()
            authRepository.logOut()
        }
    }
    private var _user = MutableStateFlow(User(
        id = "0",
        email = "0",
        name = "0",
        surname = "0",
    ))
    var user = _user.asStateFlow()

    init {
        getUser()
    }


    private fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase()
            user.let { user ->
                _user.value = user!!
            }


        }

    }


}