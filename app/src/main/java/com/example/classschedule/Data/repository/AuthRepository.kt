package com.example.classschedule.Data.repository

import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Domain.entity.User
import com.example.classschedule.Domain.entity.toUserForDataBase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import javax.inject.Inject



class AuthRepository@Inject constructor(
    val supabaseClient: SupabaseClient,
    val userDao: UserDao
) {
    suspend fun register(email: String, password: String, name: String, surname: String) : Result<Unit> {
        return runCatching{
            userDao.clearAll()
            supabaseClient.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("name", name)
                    put("surname", surname)
                }
            }
            val user = supabaseClient.auth.currentUserOrNull()

            if(user != null) {
                val uuid = user.id
                userDao.add(
                    User(
                        id = uuid,
                        email = email,
                        name = name,
                        surname = surname,
                        password = password,
                    ).toUserForDataBase()
                )
            }
        }

    }

    suspend fun login(email: String, password: String) : Result<Unit>{
        return runCatching {
            userDao.clearAll()
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val user = supabaseClient.auth.currentUserOrNull()
            if (user != null) {
                val name = user.userMetadata?.get("name")?.jsonPrimitive?.content
                val surname = user.userMetadata?.get("surname")?.jsonPrimitive?.content
                val uuid = user.id
                val email = email
                val password = password
                userDao.add(
                    User(
                        id = uuid,
                        email = email,
                        name = name?:"NULL_NAME",
                        surname = surname?:"NULL_SURNAME",
                        password = password
                    ).toUserForDataBase()
                )
            }
        }
    }
}