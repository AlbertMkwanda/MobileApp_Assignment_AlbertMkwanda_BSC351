package com.example.campusguide.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.campusguide.CampusLocation
import com.example.campusguide.LocationStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val _locations = MutableStateFlow<List<CampusLocation>>(emptyList())
    val locations: StateFlow<List<CampusLocation>> = _locations

    init {
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            _locations.value = LocationStorage.getLocations(getApplication())
        }
    }

    fun addLocation(location: CampusLocation) {
        viewModelScope.launch {
            val currentLocations = _locations.value.toMutableList()
            currentLocations.add(location)
            LocationStorage.saveLocations(getApplication(), currentLocations)
            _locations.value = currentLocations
        }
    }
}
