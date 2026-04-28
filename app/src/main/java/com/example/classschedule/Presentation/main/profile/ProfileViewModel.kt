package com.example.classschedule.Presentation.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Domain.dataBase.toUser
import com.example.classschedule.Domain.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    val userDao: UserDao
) : ViewModel(){

    private var _user = MutableStateFlow(User(
        id = "0",
        email = "0",
        name = "0",
        surname = "0",
        password = "0"
    ))
    var user = _user.asStateFlow()

    init {
        getUser()
    }


    private fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUser()?.toUser()
            user.let { user ->
                _user.value = user!!
            }


        }

    }

    fun exitFromAcc(){
        viewModelScope.launch(Dispatchers.IO) {
            userDao.clearAll()
        }
    }

}