package com.example.classschedule.Domain.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.classschedule.Domain.entity.User


@Entity(tableName = "User")
data class UserForDataBase (
    @PrimaryKey()
    val id: String,
    val name: String,
    val surname: String,
    val password: String,
    val email: String,

)

fun UserForDataBase.toUser() : User{
    return User(
        id = id,
        name = name,
        surname = surname,
        password = password,
        email = email
    )
}
