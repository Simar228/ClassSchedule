package com.example.classschedule.Presentation.entrance.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Data.repository.AuthRepository
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.util.UiText
import com.example.classschedule.Presentation.util.suppabaseErrorHandler
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RegisterViewModel.Factory::class)
class RegisterViewModel @AssistedInject constructor(
    val supabaseClient: SupabaseClient,
    val authRepository: AuthRepository,
    @Assisted val navigate: (Screen) -> Unit
) : ViewModel() {


    private val _errorEvents = Channel<UiText>(Channel.BUFFERED)
    val errorEvents = _errorEvents.receiveAsFlow()
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailEditEvent -> {
                _state.update { it.copy(email = event.email) }
                canNavigate()
            }

            is RegisterEvent.NameEditEvent -> {
                _state.update { it.copy(name = event.name) }
                canNavigate()
            }

            is RegisterEvent.PasswordEditEvent -> {
                _state.update { it.copy(password = event.password) }
                canNavigate()
            }

            is RegisterEvent.SurnameEditEvent -> {
                _state.update { it.copy(surname = event.surname) }
                canNavigate()
            }

            is RegisterEvent.EmailFocusLostEvent -> {
                val isValid = Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
                _state.update { it.copy(isValidEmail = isValid) }
                canNavigate()
            }

            is RegisterEvent.JoinButtonEvent -> viewModelScope.launch {
                _state.update { it.copy(canNavigateToMainScreen = false) }
                val isRegister = authRepository.register(
                    email = state.value.email,
                    password = state.value.password,
                    name = state.value.name,
                    surname = state.value.surname
                )
                val errorUiText = isRegister.suppabaseErrorHandler(
                    onFailure = {
                        failuerSB()
                    }, tag = "Register"
                ) {
                    Log.d("!!!", "Success Register")
                    navigate(Screen.Main)
                }
                _errorEvents.send(errorUiText)
                _state.update { it.copy(canNavigateToMainScreen = true) }
            }
        }
    }


    private fun failuerSB() {
        viewModelScope.launch {
            _state.update { it.copy(canNavigateToMainScreen = false) }
        }
    }

    private fun canNavigate() {
        if (
            Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
            &&
            state.value.password.length >= 5
            &&
            state.value.surname.isNotBlank()
            &&
            state.value.name.isNotBlank()
        ) {
            _state.update { it.copy(canNavigateToMainScreen = true) }
        } else _state.update { it.copy(canNavigateToMainScreen = false) }
    }

    @AssistedFactory
    interface Factory {
        fun create(navigate: (Screen) -> Unit): RegisterViewModel
    }

}