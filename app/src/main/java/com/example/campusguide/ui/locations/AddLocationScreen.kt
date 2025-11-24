package com.example.campusguide.ui.locations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.campusguide.CampusLocation
import com.example.campusguide.ui.utils.rememberCurrentLocation
import com.example.campusguide.ui.viewmodels.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationScreen(
    navController: NavController,
    locationViewModel: LocationViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }

    rememberCurrentLocation { lat, lon ->
        latitude = lat
        longitude = lon
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add New Location") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Location Name") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("e.g., Main Library, Physics Lab") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Location Type") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("e.g., Office, Lab, Lecture Hall") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Latitude: $latitude")
            Text("Longitude: $longitude")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (name.isNotBlank() && type.isNotBlank()) {
                        val newLocation = CampusLocation(
                            name = name,
                            type = type,
                            latitude = latitude,
                            longitude = longitude
                        )
                        locationViewModel.addLocation(newLocation)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Location")
            }
        }
    }
}
