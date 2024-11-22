package com.example.maplocations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maplocations.data.Location
import com.example.maplocations.data.LocationRepository
import com.example.maplocations.data.LocationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    private var _locations = emptyList<Location>()
    private val _sortedLocations = MutableStateFlow<List<Location>>(emptyList())
    val locations: StateFlow<List<Location>> = _sortedLocations

    init {
        fetchLocations()
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            try {
                val fetchedLocations = locationRepository.getLocations()
                _locations = fetchedLocations
                filterByLocationType(LocationType.RESTAURANT)
            } catch (e: Exception) {
                println("Error fetching locations: ${e.message}")
            }
        }
    }

    fun filterByLocationType(locationType: LocationType) {
        _sortedLocations.value = _locations.filter { it.locationType == locationType }
    }
}