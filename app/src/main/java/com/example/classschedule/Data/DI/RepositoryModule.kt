package com.example.classschedule.Data.DI

import com.example.classschedule.Data.repository.AuthRepositoryImpl
import com.example.classschedule.Data.repository.GradesRepositoryImpl
import com.example.classschedule.Data.repository.LessonsRepositoryImpl
import com.example.classschedule.Data.repository.RoomRepositoryImpl
import com.example.classschedule.Domain.repository.AuthRepository
import com.example.classschedule.Domain.repository.GradesRepository
import com.example.classschedule.Domain.repository.LessonsRepository
import com.example.classschedule.Domain.repository.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGradesRepository(
        gradesRepositoryImpl: GradesRepositoryImpl
    ): GradesRepository

    @Binds
    @Singleton
    abstract fun bindLessonsRepository(
        lessonsRepositoryImpl: LessonsRepositoryImpl
    ): LessonsRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl
    ): RoomRepository

}