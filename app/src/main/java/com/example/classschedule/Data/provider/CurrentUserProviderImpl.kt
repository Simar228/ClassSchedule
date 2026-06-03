package com.example.classschedule.Data.provider

import com.example.classschedule.Data.mapper.toUser
import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.provider.CurrentUserProvider
import com.example.classschedule.Domain.repository.RoomRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class CurrentUserProviderImpl @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val roomRepository: RoomRepository,
) : CurrentUserProvider {

    override suspend fun getCurrentUserId(): String? {
        return supabaseClient.auth.currentUserOrNull()?.id
    }

    override suspend fun getCurrentUserProfile(): User? {
        val user = roomRepository.getUser()
        return user
    }

    override suspend fun saveProfile(user: User) {
        roomRepository.addUser(user)
    }

    override suspend fun loadProfileFromSupabase(): User? {
        return try {
            val user = supabaseClient.auth.currentUserOrNull() ?: return null

            val metadata = user.userMetadata
            val name = metadata?.get("name")?.jsonPrimitive?.content ?: ""
            val surname = metadata?.get("surname")?.jsonPrimitive?.content ?: ""

            val profile = User(
                id = user.id,
                email = user.email ?: "",
                name = name,
                surname = surname
            )

            saveProfile(profile)
            profile
        } catch (e: Exception) {
            null
        }
    }
}