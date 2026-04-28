package com.example.classschedule.Presentation.util.bottomNavigation

import kotlinx.serialization.Serializable



sealed interface MainScreenNavigationRoute {
    @Serializable data object MainScreen : MainScreenNavigationRoute
    @Serializable data object GeneralEstimatesScreen : MainScreenNavigationRoute
    @Serializable data object ProfileScreen : MainScreenNavigationRoute



}