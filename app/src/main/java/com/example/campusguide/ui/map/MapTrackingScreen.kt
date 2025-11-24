package com.example.campusguide.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.campusguide.R
import com.example.campusguide.ui.utils.rememberCurrentLocation
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.preference.PreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTrackingScreen(
    navController: NavController,
    locationName: String,
    latitude: Double,
    longitude: Double
) {
    var userLatitude by remember { mutableStateOf(0.0) }
    var userLongitude by remember { mutableStateOf(0.0) }

    rememberCurrentLocation { lat, lon ->
        userLatitude = lat
        userLongitude = lon
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(locationName) })
        }
    ) { paddingValues ->
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            factory = { context ->
                Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(18.0)
                }
            },
            update = { mapView ->
                mapView.overlays.clear()

                // Destination marker
                val destinationMarker = Marker(mapView)
                destinationMarker.position = GeoPoint(latitude, longitude)
                destinationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                destinationMarker.title = locationName
                mapView.overlays.add(destinationMarker)

                // User location marker
                if (userLatitude != 0.0 && userLongitude != 0.0) {
                    val userMarker = Marker(mapView)
                    userMarker.position = GeoPoint(userLatitude, userLongitude)
                    userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    userMarker.icon = ContextCompat.getDrawable(mapView.context, R.drawable.ic_user_location)
                    userMarker.title = "My Location"
                    mapView.overlays.add(userMarker)
                }

                mapView.controller.setCenter(GeoPoint(latitude, longitude))
                mapView.invalidate()
            }
        )
    }
}
