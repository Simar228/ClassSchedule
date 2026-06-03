package com.example.classschedule.Presentation.mainActivity

import androidx.credentials.exceptions.domerrors.NetworkError
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Domain.model.User
import com.example.classschedule.Domain.provider.CurrentUserProvider
import com.example.classschedule.Domain.utill.NetworkObserver
import com.example.classschedule.Presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(

    private val supabaseClient: SupabaseClient,
    private val networkObserver: NetworkObserver,
    private val currentUserProvider: CurrentUserProvider
) : ViewModel() {

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent = _errorEvent.asSharedFlow()
    private val _startScreen = MutableStateFlow<Screen?>(null)
    val startScreen = _startScreen.asStateFlow()
    val isOnline = networkObserver.isAvailable


    init {
        viewModelScope.launch {
           observeSession()
        }
    }

    private fun observeSession() {
        supabaseClient.auth.sessionStatus
            .onEach { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        val currentSession = status.session
                        val user = currentSession.user
                        if (user != null) {
                            val userId: String = user.id
                            val email: String? = user.email
                            val metadata = user.userMetadata
                            val name: String = metadata?.get("name")?.jsonPrimitive?.contentOrNull ?: "Не указано"
                            val surname: String = metadata?.get("surname")?.jsonPrimitive?.contentOrNull ?: "Не указано"
                            currentUserProvider.saveProfile(User(
                                id = userId,
                                email = email!!,
                                name = name,
                                surname = surname
                            ))
                        }
                        _startScreen.value = Screen.Main
                    }
                    is SessionStatus.NotAuthenticated -> {
                        // Сессии нет или пользователь нажал "Выйти"
                        _startScreen.value = Screen.DefaultEntrance
                    }
                    SessionStatus.Initializing -> {
                        // Библиотека прямо сейчас инициализируется и считывает токен с диска Android
                        // Здесь можно показать крутилку (ProgressBar)
                    }
                    is SessionStatus.RefreshFailure -> {
                        // Обработка ошибки обновления токена
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}