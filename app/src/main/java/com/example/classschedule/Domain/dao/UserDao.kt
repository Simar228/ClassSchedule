package com.example.classschedule.Domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.classschedule.Domain.dataBase.UserForDataBase


@Dao
interface UserDao {
    @Insert
    suspend fun add(user: UserForDataBase)
    @Delete
    suspend fun remove(user: UserForDataBase)
    @Query("DELETE FROM User")
    suspend fun clearAll()
    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getUser(): UserForDataBase?
}