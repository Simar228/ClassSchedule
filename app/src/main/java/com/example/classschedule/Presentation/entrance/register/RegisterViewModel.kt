package com.example.classschedule.Presentation.entrance.register

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Data.repository.AuthRepository
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.util.UiText
import com.example.classschedule.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.exception.AuthRestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException

import javax.inject.Inject

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


    fun onEvent(event: RegisterEvent){
        when(event){
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
                isRegister.onSuccess {
                    Log.d("!!!", "Success Register")
                    navigate(Screen.Main)

                }
                isRegister.onFailure { e -> Log.d("!!!", e.toString())
                    failuerSB(e)
                }
                _state.update { it.copy(canNavigateToMainScreen = true) }
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
                    _errorEvents.send(UiText.DynamicString("Unknown error: ${e.localizedMessage}"))
                }
            }
            _state.update { it.copy(canNavigateToMainScreen = true) }
        }
    }
    private fun canNavigate(){
         if(
             Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
             &&
             state.value.password.length >= 6
             &&
             state.value.surname.isNotBlank()
             &&
             state.value.name.isNotBlank()
                 ){
             _state.update { it.copy(canNavigateToMainScreen = true) }
         }
        else _state.update { it.copy(canNavigateToMainScreen = false) }
    }

@AssistedFactory
interface Factory{
    fun create(navigate: (Screen) -> Unit) : RegisterViewModel
}

}