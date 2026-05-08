package com.example.classschedule.Presentation.util.bottomNavigation

import android.R.attr.maxWidth
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.Index
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.liquid
import io.github.fletchmckee.liquid.rememberLiquidState


private val navItems = listOf(
    BottomNavItem(
        icon = Icons.Default.Class,
        titleResId = 0,
        route = MainScreenNavigationRoute.MainScreen
    ),
    BottomNavItem(
        icon = Icons.Default.BarChart,
        titleResId = 0,
        route = MainScreenNavigationRoute.GeneralEstimatesScreen,
    ),
    BottomNavItem(
        icon = Icons.Default.PersonOutline,
        titleResId = 0,
        route = MainScreenNavigationRoute.ProfileScreen
    )
)


@Composable
fun MyBottomNavigation(navController: NavController, liquidState: LiquidState) {

    val liquidStateForNavigationBar = rememberLiquidState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val selectedList = remember {
        mutableStateListOf(
            mutableStateOf(true),
            mutableStateOf(false),
            mutableStateOf(false)
        )
    }
    Box() {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .height(80.dp)
                .fillMaxWidth(0.9f)
                .liquid(liquidState) {
                    // Defaults to 0.dp
                    frost = 11.dp //прозрачность
                    // Defaults to CircleShape
                    shape = RoundedCornerShape(25)
                    // Defaults to 0.25f
                    refraction = 0.5f //искажение
                    // Defaults to 0.25f
                    curve = 0.5f //кривизная по краям
                    // Defaults to 0f
                    edge = 0f
                    // Defaults to Color.Unspecified
                    tint = Color.White.copy(alpha = 0.2f)
                    // Defaults to 1f
                    saturation = 1f //нассыщенность
                    // Defaults to 0f
                    dispersion = 0f //красочность
                }


        ) {

            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any { it.route == item.route::class.qualifiedName || it.route == item.route.toString() } == true,
                        onClick = {
                            selectedList.forEachIndexed { _index, state ->
                                state.value = (index == _index)
                            }
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) { //выкидывает скрины кроме первого и последнего
                                    saveState = true //сохраняет данные на экранах
                                }

                                launchSingleTop =
                                    true //при нажатии на один и тот же экран не создает копии

                                restoreState = true //Заставляет восстановить страницу
                            }
                        },
                        icon = {
                            AnimatedNavigationIcon(
                                index = index,
                                imageVector = item.icon,
                                isSelected = selectedList[index].value,
                                liquidState = liquidStateForNavigationBar
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = Color(0xFF5856D6),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color(0xFF5856D6),
                            unselectedTextColor = Color.Gray,
                            disabledIconColor = Color.Transparent,
                            disabledTextColor = Color.Transparent
                        ),
                        interactionSource = remember { MutableInteractionSource() },

                        )
                }

            }

        }
        val index = selectedList.indexOfFirst {
            it.value
        }
        LiquidCircle(liquidStateForNavigationBar, index)
    }

}


@Composable
private fun AnimatedNavigationIcon(
    index: Int,
    liquidState: LiquidState,
    imageVector: ImageVector,
    isSelected: Boolean
) {
    var isClicked by remember { mutableStateOf(false) }

    LaunchedEffect(isSelected) {

        isClicked = isSelected

    }
    val scale by animateFloatAsState(
        targetValue = if (isClicked) 1.18f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "IconScale",
        finishedListener = {
        }
    )
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier
            .liquefiable(liquidState)
            .padding(top = 20.dp)
            .graphicsLayer(
                scaleY = scale,
                scaleX = scale
            )
            .size(75.dp)

    )
}

@Composable
private fun LiquidCircle(liquidState: LiquidState, index: Int) {
    val density = LocalDensity.current



    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth(0.9f)
        .height(80.dp)) {
        val tabWidth = maxWidth // Вся ширина бара
        val singleTabWidth = tabWidth / 3 // Ширина одной вкладки
        val tabWidthPx = with(density) { maxWidth.toPx() }
        val singleTabWidthPx = tabWidthPx / 3

        // Находим локальный центр целевой вкладки в пикселях
        val targetX = (singleTabWidthPx * index) + (singleTabWidthPx / 2f)

        // 2. Анимация через Animatable (как в вашем исходном коде для работы эффекта liquid)
        val animatedX = remember { Animatable(targetX) }
        LaunchedEffect(targetX) {
            animatedX.animateTo(
                targetValue = targetX,
                animationSpec = tween(
                    durationMillis = 350,
                    easing = FastOutSlowInEasing
                )
            )
        }
        Box(
            modifier = Modifier
                .graphicsLayer {
                    val centerOfTab = (singleTabWidth.toPx() * index) + (singleTabWidth.toPx() / 2f)
                    translationX = animatedX.value - (size.width / 2f)
                    translationY = 0f
                }
                .size(60.dp)
                .align(Alignment.CenterStart)
                .liquid(liquidState) {
                    frost = 1.dp //прозрачность
                    // Defaults to CircleShape
                    shape = RoundedCornerShape(25)
                    // Defaults to 0.25f
                    refraction = 0f
                    // Defaults to 0.25f
                    curve = 0.1f
                    // Defaults to 0f
                    edge = 0f
                    // Defaults to Color.Unspecified
                    tint = Color.White.copy(alpha = 0.2f)
                    // Defaults to 1f
                    saturation = 1f //нассыщенность
                    // Defaults to 0f
                    dispersion = 0f //красочность
                }


        ) {
        }
    }
}