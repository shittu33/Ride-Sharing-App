package com.example.lincride.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Widget to display waiting time in the Rider Action bottom sheet
 * Shows time in format "04:45" with "Waiting time" label below
 */
@Composable
fun WaitingTimeWidget(
    waitingTime: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        // Time display (e.g., "04:45")
        Text(
            text = waitingTime,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp,
            color = Color(0xFF2A2A2A) // #2A2A2A from Figma
        )
        
        // Label
        Text(
            text = "Waiting time",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp,
            color = Color(0xFF656565) // #656565 from Figma
        )
    }
}
