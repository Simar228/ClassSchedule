package com.example.classschedule.Data.repository

import com.example.classschedule.Data.dao.UserDao
import com.example.classschedule.Data.mapper.toUserForDataBase
import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    val supabaseClient: SupabaseClient,
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Result<Unit> {
        return runCatching {
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("name", name)
                    put("surname", surname)
                }
            }
        }
    }

    override suspend fun logOut(): Result<Unit> {
        return runCatching {
         supabaseClient.auth.signOut()
        }

    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return runCatching {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }
}
