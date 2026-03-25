package com.example.classschedule.Data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserDto(
    @SerialName("id")val id: String,
    @SerialName("email")val email: String,
    @SerialName("name")val name: String,
    @SerialName("surname")val surname: String,
    @SerialName("password")val password: String,

)