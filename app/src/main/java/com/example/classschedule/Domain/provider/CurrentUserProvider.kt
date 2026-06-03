package com.example.classschedule.Domain.provider

import com.example.classschedule.Domain.model.User

interface CurrentUserProvider {

    suspend fun getCurrentUserId(): String?

    suspend fun getCurrentUserProfile(): User?
    suspend fun saveProfile(user: User)
    suspend fun loadProfileFromSupabase(): User?
}