package com.example.classschedule.Presentation.entrance.defualtEntrance

import android.content.Context
import android.util.Log
import android.util.Patterns

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classschedule.Data.dto.UserDto
import com.example.classschedule.Data.repository.AuthRepository
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.util.UiText
import com.example.classschedule.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Phone
import io.github.jan.supabase.postgrest.from
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel(assistedFactory = DefaultEntranceViewModel.Factory::class)
class DefaultEntranceViewModel @AssistedInject constructor(
    private val authRepository: AuthRepository,
    private val userDao: UserDao,
    @Assisted val navigate: (Screen) -> Unit

): ViewModel() {






    private val _errorEvents = Channel<UiText>(Channel.BUFFERED)
    val errorEvents = _errorEvents.receiveAsFlow()

   private val _state = MutableStateFlow(DefualtEntranceState())
    val state = _state.asStateFlow()

    fun onEvent(event: DefualtEntranceEvent) {
        canNavigate()
        when (event) {
            is DefualtEntranceEvent.EmailEditEvent -> {

                _state.update { it.copy(email = event.email)
                }
            }
            is DefualtEntranceEvent.PasswordEditEvent -> {

                _state.update { it.copy(password = event.password) }

            }
            is DefualtEntranceEvent.EmailFocusLostEvent -> {
                val isValid = Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
                _state.update { it.copy(isValidEmail = isValid) }
            }

            DefualtEntranceEvent.LoginButtonEvent -> {

                viewModelScope.launch {
                    val login = authRepository.login(
                        email = state.value.email,
                        password = state.value.password
                    )
                    login.onSuccess {

                        navigate(Screen.Main)
                    }
                    login.onFailure { exception ->
                        Log.e("Login", exception.toString())
                        failuerSB(exception)
                    }

                }
            }
        }
    }


    private fun failuerSB(e: Throwable) {
        viewModelScope.launch {
            _state.update { it.copy(canNavigateToMainScreen = false) }
            when (e) {

                is ConnectException,
                is UnknownHostException,
                is HttpRequestTimeoutException -> {
                    _errorEvents.send(UiText.Resource(R.string.invalid_internet))
                }


                is AuthRestException -> {
                    val uiText = when (e.errorCode.toString()) {
                        "user_already_exists" -> UiText.Resource(R.string.user_already_exists)
                        "over_email_send_rate_limit" -> UiText.Resource(R.string.over_email_send_rate_limit)
                        "weak_password" -> UiText.Resource(R.string.weak_password)
                        "invalid_credentials" -> UiText.Resource(R.string.invalid_credentials)
                        else -> {
                            UiText.DynamicString(e.message ?: "Unknown error")
                        }
                    }
                    _errorEvents.send(uiText)
                }
                else -> {
                    _errorEvents.send(UiText.DynamicString("Неизвестная ошибка: ${e.localizedMessage}"))
                }
            }
            _state.update { it.copy(canNavigateToMainScreen = true) }
        }
    }

    private fun canNavigate() {
        if(
            Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
            &&
            state.value.password.length >= 6
        ){
            _state.update { it.copy(canNavigateToMainScreen = true) }
        }
        else _state.update { it.copy(canNavigateToMainScreen = false) }

    }




    @AssistedFactory
    interface Factory {
        fun create(navigate: (Screen) -> Unit): DefaultEntranceViewModel
    }
}




