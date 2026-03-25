package com.example.classschedule.Presentation.entrance.defualtEntrance

data class DefualtEntranceState (

    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean = true,
    var canNavigateToMainScreen: Boolean = false


)

sealed interface DefualtEntranceEvent {
    data class EmailEditEvent(val email: String) : DefualtEntranceEvent
    data class PasswordEditEvent(val password: String) : DefualtEntranceEvent
    data object EmailFocusLostEvent : DefualtEntranceEvent
    data object LoginButtonEvent : DefualtEntranceEvent
}