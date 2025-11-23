package com.example.campusguide.ui.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MapTrackingScreen(
    navController: NavController,
    locationName: String,
    latitude: Double,
    longitude: Double
) {
    Text("Map Tracking Screen for $locationName at ($latitude, $longitude)")
}
