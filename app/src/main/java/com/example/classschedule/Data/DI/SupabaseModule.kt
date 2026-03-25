package com.example.classschedule.Data.DI

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {


    @Singleton
    @Provides
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://rehupmfrntrwlykqjwna.supabase.co",
            supabaseKey = "sb_publishable_Kmm6ke7t5yKvsT36jcFO0g_f8SIFi32"
        ) {
            install(Postgrest)
            install(Auth)
        }
    }
}