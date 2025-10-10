package com.example.lincride.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lincride.ui.theme.LocalColors

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ride History",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            color = LocalColors.current.textPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn {
            items(5) { index ->
                // Placeholder history item
                Text(
                    text = "Past Ride ${index + 1}",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = LocalColors.current.textSecondary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}
