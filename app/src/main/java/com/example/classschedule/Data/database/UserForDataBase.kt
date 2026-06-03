package com.example.classschedule.Data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.classschedule.Domain.model.User


@Entity(tableName = "User")
data class UserForDataBase (
    @PrimaryKey()
    val id: String,
    val name: String,
    val surname: String,
    val email: String,

)


