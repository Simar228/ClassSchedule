package com.example.classschedule.Data.DI

import android.content.Context
import androidx.room.Room
import com.example.classschedule.Data.database.UserDatabase
import com.example.classschedule.Data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideUserDataBase(@ApplicationContext context: Context) : UserDatabase{
        return Room.databaseBuilder<UserDatabase>(context, UserDatabase::class.java, "user_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase): UserDao{
        return userDatabase.getUserDao()
    }


}