package com.example.maplocations.data

enum class LocationType {
    RESTAURANT,
    MUSEUM,
    PARK,
    CAFE,
    BAR,
    LANDMARK,
    NONE
}

data class Location(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val locationType: LocationType,
    val name: String,
    val description: String,
    val estimatedRevenueMillions: Number
)