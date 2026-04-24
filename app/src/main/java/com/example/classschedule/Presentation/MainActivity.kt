package com.example.classschedule.Presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.classschedule.Data.repository.AuthRepository
import com.example.classschedule.Domain.dao.UserDao
import com.example.classschedule.Presentation.navigation.MainNav
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.ui.theme.ClassScheduleTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.classschedule.Data.util.IsInternetAvailable
import com.example.classschedule.Data.util.NetworkMonitor
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var authRepository: AuthRepository
    @Inject lateinit var userDao: UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ClassScheduleTheme {
                val navController = rememberNavController()
                var startScreen by remember { mutableStateOf<Screen?>(null) }
                var retryTrigger by remember { mutableIntStateOf(0) }
                val isOnline by NetworkMonitor.isAvailable.collectAsStateWithLifecycle()
                splashScreen.setKeepOnScreenCondition {
                    startScreen == null
                }

                LaunchedEffect(isOnline) {
                    if(!isOnline){
                        navController.navigate(Screen.NoInternet)
                    }
                }

                LaunchedEffect(retryTrigger) {
                    startScreen = tryEnter()
                }




                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (startScreen != null) {

                        MainContent(
                            startScreen = startScreen!!,
                            modifier = Modifier.padding(innerPadding),
                            navHostController = navController,
                            context = this@MainActivity
                        )

                }else {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            }



        }
    }



    suspend fun tryEnter(): Screen {
        val user = userDao.getUser() ?: return Screen.DefaultEntrance
        val stateLogin = authRepository.login(user.email, user.password)
        return stateLogin.fold(
                onSuccess = {
                    Screen.Main
                },
                onFailure = {
                    Screen.DefaultEntrance
                }
            )

    }
}


@Composable
private fun MainContent(
    modifier: Modifier,
    navHostController: NavHostController,
    startScreen: Screen,
    context: Context,
){
    MainNav(startScreen, navHostController, context = context)
}



