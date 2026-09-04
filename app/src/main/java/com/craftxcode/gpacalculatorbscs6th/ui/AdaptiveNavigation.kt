package com.craftxcode.gpacalculatorbscs6th.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.craftxcode.gpacalculatorbscs6th.navigation.Screen

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: Screen
)

val BottomNavItems = listOf(
    NavItem("Dashboard", Icons.Default.Dashboard, Screen.Dashboard),
    NavItem("Calculate", Icons.Default.Calculate, Screen.GPACalculator),
    NavItem("Records", Icons.Default.History, Screen.AcademicRecords),
    NavItem("Settings", Icons.Default.Settings, Screen.Settings)
)

@Composable
fun AdaptiveScaffold(
    windowWidthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isSplash = currentDestination?.hasRoute<Screen.Splash>() ?: true
    val isWeight = currentDestination?.hasRoute<Screen.WeightCalculator>() ?: false

    if (isSplash || isWeight) {
        content(PaddingValues(0.dp))
        return
    }

    if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationRail {
                Spacer(Modifier.weight(1f))
                BottomNavItems.forEach { item ->
                    NavigationRailItem(
                        selected = currentDestination?.hasRoute(item.route::class) ?: false,
                        onClick = { 
                            navController.navigate(item.route) {
                                popUpTo(Screen.Dashboard) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
                Spacer(Modifier.weight(1f))
            }
            Box(modifier = Modifier.weight(1f)) {
                content(PaddingValues(0.dp))
            }
        }
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    BottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hasRoute(item.route::class) ?: false,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Screen.Dashboard) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) { padding ->
            content(padding)
        }
    }
}
