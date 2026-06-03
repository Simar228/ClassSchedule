package com.example.classschedule.Data.mapper

import com.example.classschedule.Data.database.UserForDataBase
import com.example.classschedule.Data.dto.UserDto
import com.example.classschedule.Domain.model.User


fun User.toUserForDataBase(): UserForDataBase {
    return UserForDataBase(
        id = id,
        name = name,
        surname = surname,
        email = email
    )
}

fun UserForDataBase.toUser() : User{
    return User(
        id = id,
        name = name,
        surname = surname,
        email = email
    )
}
