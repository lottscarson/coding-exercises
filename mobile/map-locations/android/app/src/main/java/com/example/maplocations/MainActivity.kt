package com.example.maplocations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.maplocations.data.LocationRepository
import com.example.maplocations.ui.theme.MapLocationsTheme
import com.example.maplocations.views.MapScreen

class MainActivity : ComponentActivity() {

    private val locationRepository = LocationRepository() // Inject the repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = LocationViewModel(locationRepository)
        setContent {
            MapLocationsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapScreen(innerPadding, viewModel)
                }
            }
        }
    }
}


