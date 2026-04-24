package com.example.classschedule.Presentation.entrance.defualtEntrance

import android.util.Patterns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classschedule.Data.repository.AuthRepository
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.util.suppabaseErrorHandler
import com.example.classschedule.Presentation.util.UiText
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                    val errorUiText = login.suppabaseErrorHandler(onFailure = {failuerSB()}, tag = "DefaultEntrance"){
                        navigate(Screen.Main)
                    }
                    _errorEvents.send(errorUiText)

                }
            }
        }
    }


    private fun failuerSB() {
        viewModelScope.launch {
            _state.update { it.copy(canNavigateToMainScreen = false) }
        }
    }

    private fun canNavigate() {
        if(
            Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
            &&
            state.value.password.length >= 5
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




