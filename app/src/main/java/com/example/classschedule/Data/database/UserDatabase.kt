package com.example.classschedule.Data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.classschedule.Data.dao.UserDao


@Database( entities = [UserForDataBase::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}