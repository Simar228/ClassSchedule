package com.example.classschedule.Presentation

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.classschedule.Data.util.IsInternetAvailable
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
                splashScreen.setKeepOnScreenCondition {
                    startScreen == null
                }

                LaunchedEffect(retryTrigger) {
                    startScreen = tryEnter()
                }




                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (startScreen != null) {

                        MainContent(
                            onRetry = {
                                startScreen = null
                                retryTrigger++
                            },
                            startScreen = startScreen!!,
                            modifier = Modifier.padding(innerPadding),
                            navHostController = navController
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
        val isInternetAvailable = IsInternetAvailable(this)
        if (!isInternetAvailable) return Screen.NoInternet
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
    onRetry: () -> Unit,
    modifier: Modifier,
    navHostController: NavHostController,
    startScreen: Screen
){
    MainNav(onRetry, startScreen, navHostController)
}



