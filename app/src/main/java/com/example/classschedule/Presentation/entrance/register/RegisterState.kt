package com.example.classschedule.Presentation.entrance.register

data class RegisterState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean = true,
    var canNavigateToMainScreen: Boolean = false

)

sealed interface RegisterEvent{
    data class NameEditEvent(val name: String) : RegisterEvent
    data class SurnameEditEvent(val surname: String) : RegisterEvent
    data class EmailEditEvent(val email: String) : RegisterEvent
    data class PasswordEditEvent(val password: String) : RegisterEvent
    data object EmailFocusLostEvent : RegisterEvent
    data object JoinButtonEvent : RegisterEvent
}