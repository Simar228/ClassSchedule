package com.example.classschedule.Data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.classschedule.Data.database.UserForDataBase


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: UserForDataBase)
    @Delete
    suspend fun remove(user: UserForDataBase)
    @Query("DELETE FROM User")
    suspend fun clearAll()
    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getUser(): UserForDataBase?
}