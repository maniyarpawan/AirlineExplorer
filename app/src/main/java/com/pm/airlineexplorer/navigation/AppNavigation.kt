package com.pm.airlineexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pm.airlineexplorer.airlineexplorerlist.ui.AirlineScreen
import com.pm.airlineexplorer.details.ui.AirlineDetailScreen

@Composable
internal fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AirlineScreen.route) {
        composable(route = Screen.AirlineScreen.route) {
            AirlineScreen(navController = navController)
        }

        composable(route = Screen.AirlineDetailScreen.route) {
            AirlineDetailScreen()
        }
    }
}