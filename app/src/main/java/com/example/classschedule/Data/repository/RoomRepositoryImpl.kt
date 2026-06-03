package com.example.classschedule.Data.repository

import com.example.classschedule.Data.dao.UserDao
import com.example.classschedule.Data.database.UserForDataBase
import com.example.classschedule.Data.mapper.toUser
import com.example.classschedule.Data.mapper.toUserForDataBase

import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : RoomRepository {
    override suspend fun addUser(user: User) {
        val userForDataBase = user.toUserForDataBase()
        userDao.add(userForDataBase)
    }

    override suspend fun removeUser() {
        userDao.clearAll()
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()?.toUser()
    }
}