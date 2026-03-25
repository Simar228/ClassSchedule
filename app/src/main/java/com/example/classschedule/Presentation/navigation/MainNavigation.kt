package com.example.classschedule.Presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.classschedule.Presentation.entrance.defualtEntrance.DefualtEntranceScreen
import com.example.classschedule.Presentation.entrance.register.RegisterScreen
import com.example.classschedule.Presentation.utilScreen.NoInternetScreen

@Composable

fun MainNav(
    onRetry: () -> Unit,
    startScreen: Screen,
    navHostController : NavHostController,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navHostController,
        startDestination = startScreen
    ){
        composable<Screen.Register> {
            RegisterScreen { navigateTo ->
                navHostController.navigate(navigateTo)
            }
        }
        composable<Screen.Main> {

        }
        composable<Screen.DefaultEntrance> {
            DefualtEntranceScreen { navigateTo ->
                navHostController.navigate(navigateTo) }
        }
        composable<Screen.NoInternet> {
            NoInternetScreen { onRetry() }
        }
    }


}