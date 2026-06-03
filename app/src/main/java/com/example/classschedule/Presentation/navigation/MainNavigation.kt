package com.example.classschedule.Presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.classschedule.Presentation.main.MainScreen
import com.example.classschedule.Presentation.entrance.defualtEntrance.DefaultEntranceScreen
import com.example.classschedule.Presentation.entrance.register.RegisterScreen
import com.example.classschedule.Presentation.utilScreen.NoInternetScreen

@Composable
fun MainNav(
    startScreen: Screen,
    navHostController: NavHostController,
//    networkObserver: NetworkObserver,
) {

    NavHost(
        navController = navHostController,
        startDestination = startScreen
    ) {

        composable<Screen.Register> {
            RegisterScreen { navigateTo ->
                navHostController.navigate(navigateTo)
            }
        }
        composable<Screen.Main> {
            MainScreen(){navigateTo ->
                navHostController.navigate(navigateTo)
            }
        }
        composable<Screen.DefaultEntrance> {
            DefaultEntranceScreen { navigateTo ->
                navHostController.navigate(navigateTo)
            }
        }
        composable<Screen.NoInternet> {
            NoInternetScreen()
        }
    }


}