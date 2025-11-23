package com.example.campusguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AddLocationActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var locationDisplay: TextView
    private lateinit var saveButton: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0

    private val LOCATION_PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        nameInput = findViewById(R.id.place_name_input)
        locationDisplay = findViewById(R.id.location_coordinates_text)
        saveButton = findViewById(R.id.save_place_button)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermissions()

        saveButton.setOnClickListener {
            saveNewLocation()
        }
    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Location permission is required to save a place.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    locationDisplay.text = "Location: Lat ${"%.4f".format(currentLatitude)}, Lng ${"%.4f".format(currentLongitude)}"
                } else {
                    locationDisplay.text = "Error: Could not retrieve current location."
                }
            }
            .addOnFailureListener {
                locationDisplay.text = "Error: Failed to get location."
            }
    }

    private fun saveNewLocation() {
        val name = nameInput.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name for the place.", Toast.LENGTH_SHORT).show()
            return
        }
        if (currentLatitude == 0.0 && currentLongitude == 0.0) {
            Toast.makeText(this, "Location data not ready. Try again.", Toast.LENGTH_SHORT).show()
            return
        }

        val newLocation = CampusLocation(
            name = name,
            type = "User Added",
            latitude = currentLatitude,
            longitude = currentLongitude
        )

        // Load, add, and save the list
        val locations = LocationStorage.getLocations(this)
        locations.add(0, newLocation) // Add to the top
        LocationStorage.saveLocations(this, locations)

        Toast.makeText(this, "$name saved successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}