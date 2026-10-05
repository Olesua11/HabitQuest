package com.olesya.habitquest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.olesya.habitquest.MainViewModel
import com.olesya.habitquest.ui.screens.*
import com.olesya.habitquest.ui.theme.Ink
import com.olesya.habitquest.ui.theme.Panel

private data class NavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun HabitQuestApp(viewModel: MainViewModel) {
    val nav = rememberNavController()
    val items = listOf(
        NavItem("home", "Quest", Icons.Default.Home),
        NavItem("achievements", "Awards", Icons.Default.EmojiEvents),
        NavItem("stats", "Stats", Icons.Default.BarChart),
        NavItem("profile", "Profile", Icons.Default.Person)
    )
    val backStack by nav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        containerColor = Ink,
        bottomBar = {
            NavigationBar(containerColor = Panel) {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            nav.navigate(item.route) {
                                popUpTo(nav.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = nav, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { HomeScreen(viewModel) }
            composable("achievements") { AchievementsScreen(viewModel) }
            composable("stats") { StatsScreen(viewModel) }
            composable("profile") { ProfileScreen(viewModel) }
        }
    }
}
