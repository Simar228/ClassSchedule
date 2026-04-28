package com.example.classschedule.Presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.classschedule.Presentation.main.grades.GradesScreen
import com.example.classschedule.Presentation.main.lessons.LessonsScreen
import com.example.classschedule.Presentation.main.profile.ProfileScreen
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.util.bottomNavigation.MainScreenNavigationRoute
import com.example.classschedule.Presentation.util.bottomNavigation.MyBottomNavigation


@Composable
fun MainScreen(
     navigateTo: (Screen) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MyBottomNavigation(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MainScreenNavigationRoute.MainScreen,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<MainScreenNavigationRoute.MainScreen> {
                LessonsScreen()
            }
            composable<MainScreenNavigationRoute.GeneralEstimatesScreen> {
                GradesScreen()
            }
            composable<MainScreenNavigationRoute.ProfileScreen> {
                ProfileScreen(){ screen ->
                    navigateTo(screen)
                }
            }
        }

    }


}