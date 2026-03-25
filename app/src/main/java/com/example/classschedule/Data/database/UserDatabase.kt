package com.example.classschedule.Data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Domain.dataBase.UserForDataBase


@Database( entities = [UserForDataBase::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract class UserDataBase : RoomDatabase()

    abstract fun getUserDao(): UserDao
}