package com.example.classschedule.Data.dto

import com.example.classschedule.Domain.entity.User

class UserMapper {

    fun UserDto.toModel() = User(
        id = id,
        name = name,
        surname = surname,
        email = email,
        password = password
    )
}