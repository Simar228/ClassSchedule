package com.example.classschedule.Presentation.Main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.classschedule.Presentation.Main.generalEstimates.GeneralEstimatesScreen
import com.example.classschedule.Presentation.Main.lessons.LessonsScreen
import com.example.classschedule.Presentation.Main.lessons.LessonsState
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.util.bottomNavigation.MainScreenNavigationRoute
import com.example.classschedule.Presentation.util.bottomNavigation.MyBottomNavigation


@Composable
fun MainScreen(){
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
            composable <MainScreenNavigationRoute.GeneralEstimatesScreen>{
                GeneralEstimatesScreen()
            }
        }

    }


}