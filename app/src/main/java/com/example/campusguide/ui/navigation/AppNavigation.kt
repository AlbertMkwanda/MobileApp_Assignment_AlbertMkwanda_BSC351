package com.example.campusguide.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.campusguide.ui.locations.AddLocationScreen
import com.example.campusguide.ui.locations.LocationListScreen
import com.example.campusguide.ui.map.MapTrackingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LocationList.route) {
        composable(Screen.LocationList.route) {
            LocationListScreen(navController = navController)
        }
        composable(Screen.AddLocation.route) {
            AddLocationScreen(navController = navController)
        }
        composable(Screen.MapTracking.route) { backStackEntry ->
            val locationName = backStackEntry.arguments?.getString("locationName") ?: ""
            val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 0.0
            val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: 0.0
            MapTrackingScreen(
                navController = navController,
                locationName = locationName,
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}
