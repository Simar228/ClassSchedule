package com.example.classschedule.Domain.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User")
data class UserForDataBase (
    @PrimaryKey()
    val id: String,
    val name: String,
    val surname: String,
    val password: String,
    val email: String,

)
