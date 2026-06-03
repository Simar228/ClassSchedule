package com.example.classschedule.Domain.repository


import com.example.classschedule.Domain.model.User

interface RoomRepository {
    suspend fun addUser(user: User)
    suspend fun removeUser()
    suspend fun getUser(): User?
}