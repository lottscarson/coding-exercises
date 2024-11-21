package com.example.maplocations.network

import com.example.maplocations.data.Location
import com.example.maplocations.data.LocationType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LocationDeserializer : JsonDeserializer<Location> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Location {
        val jsonObject = json.asJsonObject
        val attributes = jsonObject.get("attributes").asJsonArray

        var locationTypeString: String? = null
        var name: String? = null
        var description: String? = null
        var estimatedRevenueMillions: Float? = null

        // Map the values in the attributes array
        for (attribute in attributes) {
            val attributeObj = attribute.asJsonObject
            when (attributeObj.get("type").asString) {
                "location_type" -> locationTypeString = attributeObj.get("value").asString
                "name" -> name = attributeObj.get("value").asString
                "description" -> description = attributeObj.get("value").asString
                "estimated_revenue_millions" -> estimatedRevenueMillions = attributeObj.get("value").asFloat
            }
        }

        // Map locationType based on locationTypeString
        var locationType: LocationType = LocationType.NONE
        if (locationTypeString != null) {
            when (locationTypeString) {
                "restaurant" -> locationType = LocationType.RESTAURANT
                "museum" -> locationType = LocationType.MUSEUM
                "park" -> locationType = LocationType.PARK
                "cafe" -> locationType = LocationType.CAFE
                "bar" -> locationType = LocationType.BAR
                "landmark" -> locationType = LocationType.LANDMARK
            }
        }

        return Location(
            id = jsonObject.get("id").asInt,
            latitude = jsonObject.get("latitude").asDouble,
            longitude = jsonObject.get("longitude").asDouble,
            locationType = locationType,
            name = name ?: "",
            description = description ?: "",
            estimatedRevenueMillions = estimatedRevenueMillions ?: 0.0
        )
    }
}