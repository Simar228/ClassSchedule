package com.example.classschedule.Domain.usecase.auth

import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.repository.AuthRepository
import com.example.classschedule.Domain.repository.RoomRepository
import io.github.jan.supabase.auth.auth
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,

) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Result<Unit> {
        return  authRepository.register(email, password, name, surname)

    }
}