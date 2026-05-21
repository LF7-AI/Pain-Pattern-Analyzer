package com.steff.painpatternanalyzer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.steff.painpatternanalyzer.ui.screens.*
import com.steff.painpatternanalyzer.viewmodel.*

sealed class Screen(val route: String, val label: String) {
    object Dashboard : Screen("dashboard", "Dashboard")
    object AddEntry  : Screen("add_entry", "Add Entry")
    object Timeline  : Screen("timeline", "Timeline")
    object Insights  : Screen("insights", "Insights")
}

@Composable
fun AppNavigation(
    dashboardViewModel: DashboardViewModel,
    addEntryViewModel: AddEntryViewModel,
    timelineViewModel: TimelineViewModel,
    insightsViewModel: InsightsViewModel
) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        Triple(Screen.Dashboard, Icons.Filled.Home, "Dashboard"),
        Triple(Screen.AddEntry,  Icons.Filled.Add,  "Add Entry"),
        Triple(Screen.Timeline,  Icons.Filled.List, "Timeline"),
        Triple(Screen.Insights,  Icons.Filled.Star, "Insights")
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { (screen, icon, label) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen(dashboardViewModel) }
            composable(Screen.AddEntry.route)  { AddEntryScreen(addEntryViewModel) }
            composable(Screen.Timeline.route)  { TimelineScreen(timelineViewModel) }
            composable(Screen.Insights.route)  { InsightsScreen(insightsViewModel) }
        }
    }
}