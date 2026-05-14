package com.example.classschedule.Presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import io.github.fletchmckee.liquid.rememberLiquidState


@Composable
fun MainScreen(
    navigateTo: (Screen) -> Unit
) {
    val navController = rememberNavController()
    val liquidState = rememberLiquidState()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MainScreenNavigationRoute.MainScreen,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            composable<MainScreenNavigationRoute.MainScreen> {
                LessonsScreen(liquidState)
            }
            composable<MainScreenNavigationRoute.GeneralEstimatesScreen> {
                GradesScreen(liquidState)
            }
            composable<MainScreenNavigationRoute.ProfileScreen> {
                ProfileScreen(liquidState) { screen ->
                    navigateTo(screen)
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            MyBottomNavigation(navController, liquidState)
        }

    }


}