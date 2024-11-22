package com.example.maplocations.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maplocations.data.Location

@Composable
fun LocationDetailsView(location: Location) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(location.name, style = MaterialTheme.typography.titleMedium)
            Text(location.description, style = MaterialTheme.typography.bodyMedium)
            Text("Location Type: ${location.locationType.name.capitalized()}.", style = MaterialTheme.typography.bodyMedium)
            Text("Estimated Revenue: $${location.estimatedRevenueMillions} million", style = MaterialTheme.typography.bodyMedium)
        }
    }
}