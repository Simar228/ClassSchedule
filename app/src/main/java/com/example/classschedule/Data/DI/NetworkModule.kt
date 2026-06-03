package com.example.classschedule.Data.DI

import com.example.classschedule.Data.util.NetworkMonitorObserverImpl
import com.example.classschedule.Domain.utill.NetworkObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindsNetworkMonitor(
        networkMonitorImpl: NetworkMonitorObserverImpl
    ): NetworkObserver

}