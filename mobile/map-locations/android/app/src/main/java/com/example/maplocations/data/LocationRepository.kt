package com.example.maplocations.data

import com.example.maplocations.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository {

    suspend fun getLocations(): List<Location> {
        return withContext(Dispatchers.IO) {
            RetrofitClient.locationService.getLocations()
        }
    }
}