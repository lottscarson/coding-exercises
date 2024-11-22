package com.example.maplocations.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maplocations.LocationViewModel
import com.example.maplocations.data.Location
import com.example.maplocations.data.LocationType
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

const val SAN_FRAN_LAT = 37.7749
const val SAN_FRAN_LONG = -122.4194

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(paddingValues: PaddingValues, viewModel: LocationViewModel) {

    val sanFran = LatLng(SAN_FRAN_LAT, SAN_FRAN_LONG)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sanFran, 15f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val locations by viewModel.locations.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedLocationType by remember { mutableStateOf(LocationType.entries[0]) }
    var selectedLocation by remember { mutableStateOf<Location?>(null) }

    Box(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapClick = { selectedLocation = null }
        ) {
            // Add the markers
            locations.forEach { location ->
                val isLocationSelected = location == selectedLocation

                Marker(
                    state = MarkerState(position = LatLng(location.latitude, location.longitude)),
                    title = location.name,
                    snippet = location.description,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (isLocationSelected) BitmapDescriptorFactory.HUE_AZURE // Selected color
                        else BitmapDescriptorFactory.HUE_RED // Default color
                    ),
                    onClick = {
                        selectedLocation = location
                        true // Prevent default behavior (camera movement)
                    }
                )
            }
        }

        Column {
            // Add a dropdown for selecting the different LocationTypes
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
            ) {
                TextField(
                    readOnly = true,
                    value = selectedLocationType.name.capitalized(),
                    onValueChange = { },
                    label = { Text("Location Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    LocationType.entries.forEach { selectionOption ->
                        if (selectionOption == LocationType.NONE) {
                            return@forEach
                        }
                        DropdownMenuItem(
                            text = { Text(selectionOption.name.capitalized()) },
                            onClick = {
                                selectedLocationType = selectionOption
                                expanded = false
                                viewModel.filterByLocationType(selectionOption)
                            }
                        )
                    }
                }
            }

            // Display the selected location details
            if (selectedLocation != null) {
                LocationDetailsView(location = selectedLocation!!)
            }
        }
    }
}

// Replacement for Kotlin's deprecated `capitalize()` function
fun String.capitalized(): String {
    return this.lowercase().replaceFirstChar {
        it.titlecase(Locale.getDefault())
    }
}
