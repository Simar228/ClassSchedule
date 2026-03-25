package com.example.classschedule.Presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object NoInternet : Screen
    @Serializable
    data object Register : Screen

    @Serializable
    data object DefaultEntrance : Screen

    @Serializable
    data object Main : Screen
}