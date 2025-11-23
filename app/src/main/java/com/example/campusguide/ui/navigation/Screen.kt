package com.example.campusguide.ui.navigation

sealed class Screen(val route: String) {
    object LocationList : Screen("location_list")
    object AddLocation : Screen("add_location")
    object MapTracking : Screen("map_tracking/{locationName}/{latitude}/{longitude}") {
        fun createRoute(locationName: String, latitude: Double, longitude: Double) =
            "map_tracking/$locationName/$latitude/$longitude"
    }
}
