package com.example.classschedule.Domain.entity

import com.example.classschedule.Domain.dataBase.UserForDataBase


data class User (

    val id: String,
    val email: String,
    val name: String,
    val surname: String,
    val password: String,

    )

fun User.toUserForDataBase() : UserForDataBase{
    return UserForDataBase(
        id = id,
        name = name,
        surname = surname,
        password = password,
        email = email
    )

}