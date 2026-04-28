package com.example.classschedule.Presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.classschedule.Data.util.NetworkMonitor
import com.example.classschedule.Data.util.isInternetAvailable
import com.example.classschedule.Presentation.main.MainScreen
import com.example.classschedule.Presentation.entrance.defualtEntrance.DefualtEntranceScreen
import com.example.classschedule.Presentation.entrance.register.RegisterScreen
import com.example.classschedule.Presentation.utilScreen.NoInternetScreen

@Composable
fun MainNav(
    startScreen: Screen,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    context: Context
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
            DefualtEntranceScreen { navigateTo ->
                navHostController.navigate(navigateTo)
            }
        }
        composable<Screen.NoInternet> {
            NoInternetScreen() {
                val isOnline = isInternetAvailable(context)
                if (isOnline) {
                    NetworkMonitor.statusInternet(true)
                    navHostController.popBackStack()
                }
            }
        }
    }


}