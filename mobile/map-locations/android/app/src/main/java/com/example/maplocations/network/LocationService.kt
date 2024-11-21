package com.example.maplocations.network

import com.example.maplocations.data.Location
import retrofit2.http.GET

interface LocationService {
    @GET("locations.json")
    suspend fun getLocations(): List<Location>
}