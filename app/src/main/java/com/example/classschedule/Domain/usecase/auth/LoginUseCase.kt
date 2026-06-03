package com.example.classschedule.Domain.usecase.auth

import com.example.classschedule.Domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> {
        return authRepository.login(
            email = email,
            password = password
        )
    }

}