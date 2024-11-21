package com.example.maplocations.network

import com.example.maplocations.data.Location
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://raw.githubusercontent.com/lottscarson/coding-exercises/master/mobile/map-locations/"

object RetrofitClient {
    // Register the custom deserializer with Gson
    val gson = GsonBuilder()
        .registerTypeAdapter(Location::class.java, LocationDeserializer())
        .create()

    val locationService: LocationService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(LocationService::class.java)
    }
}