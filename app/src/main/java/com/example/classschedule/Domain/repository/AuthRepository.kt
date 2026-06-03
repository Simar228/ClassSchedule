package com.example.classschedule.Domain.repository

import com.example.classschedule.Domain.model.User

interface AuthRepository {

    suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Result<Unit>
    suspend fun logOut(): Result<Unit>
    suspend fun login(
        email: String,
        password: String
    ): Result<Unit>
}