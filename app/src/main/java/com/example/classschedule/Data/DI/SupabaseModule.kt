package com.example.classschedule.Data.DI

import com.example.classschedule.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    val supabaseUrl = BuildConfig.SUPABASE_URL
    val supabaseKey = BuildConfig.SUPABASE_KEY




    @OptIn(SupabaseInternal::class)
    @Singleton
    @Provides
    fun provideSupabaseClient(): SupabaseClient {

        require(!supabaseUrl.contains("placeholder", ignoreCase = true)) {
            "Supabase URL не настроен. Создай файл secrets.properties из secrets.properties.example и заполни реальные значения."
        }

        require(!supabaseKey.contains("placeholder", ignoreCase = true)) {
            "Supabase Key не настроен. Создай файл secrets.properties из secrets.properties.example и заполни реальные значения."
        }

        return createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
            install(Auth) {
                alwaysAutoRefresh = true
            }
            httpConfig {
                defaultRequest {
                    header("Connection", "close")
                }
                install(io.ktor.client.plugins.HttpTimeout) {
                    requestTimeoutMillis = 15000L
                    connectTimeoutMillis = 5000L
                    socketTimeoutMillis = 8000L
                }
            }
        }
    }
}