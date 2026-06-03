package com.example.classschedule.Data.DI

import com.example.classschedule.Data.provider.CurrentUserProviderImpl
import com.example.classschedule.Data.repository.LessonsRepositoryImpl
import com.example.classschedule.Domain.provider.CurrentUserProvider
import com.example.classschedule.Domain.repository.LessonsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    @Singleton
    abstract fun bindCurrentUserProvider(
        currentUserProviderImpl: CurrentUserProviderImpl
    ): CurrentUserProvider


}