package com.example.classschedule.Presentation.mainActivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.classschedule.Presentation.navigation.MainNav
import com.example.classschedule.Presentation.navigation.Screen
import com.example.classschedule.Presentation.ui.theme.ClassScheduleTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ClassScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel: MainViewModel = viewModel()
                    val startScreen = viewModel.startScreen.collectAsStateWithLifecycle().value
                    val navController = rememberNavController()
                    val isOnline = viewModel.isOnline.collectAsStateWithLifecycle().value
                    splashScreen.setKeepOnScreenCondition {
                        startScreen == null
                    }
                    val errorMessage by viewModel.errorEvent.collectAsStateWithLifecycle(initialValue = null)

                    LaunchedEffect(errorMessage) {
                        errorMessage?.let { message ->
                            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                        }
                    }

//                    LaunchedEffect(isOnline) {
//                        if (!isOnline) {
//                            navController.navigate(Screen.NoInternet)
//                        } else {
//                            navController.popBackStack()
//                        }
//                    }






                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background
                    ) { innerPadding ->
                        if (startScreen != null) {

                            MainContent(
                                modifier = Modifier.padding(innerPadding),
                                startScreen = startScreen,
                                navHostController = navController,
                            )

                        } else {

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
    }
}
@Composable
private fun MainContent(
    modifier: Modifier,
    navHostController: NavHostController,
    startScreen: Screen,
) {
    MainNav(startScreen, navHostController)
}



