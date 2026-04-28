package com.example.classschedule.Presentation.util.bottomNavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

private val navItems = listOf(
    BottomNavItem(
        icon = Icons.Default.Class,
        titleResId = 0,
        route = MainScreenNavigationRoute.MainScreen
    ),
    BottomNavItem(
        icon = Icons.Default.BarChart,
        titleResId = 0,
        route = MainScreenNavigationRoute.GeneralEstimatesScreen
    ),
    BottomNavItem(
        icon = Icons.Default.PersonOutline,
        titleResId = 0,
        route = MainScreenNavigationRoute.ProfileScreen
    )
)


@Composable
fun MyBottomNavigation(navController: NavController)  {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    NavigationBar() {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                currentRoute?.hierarchy?.any { it.route == item.route::class.qualifiedName || it.route == item.route.toString() } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { //выкидывает скрины кроме первого и последнего
                            saveState = true //сохраняет данные на экранах
                        }

                        launchSingleTop = true //при нажатии на один и тот же экран не создает копии

                        restoreState = true //Заставляет восстановить страницу
                    }
                          },
                icon = {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(

                    indicatorColor = Color.Yellow,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Gray
                )

            )
        }

    }





}