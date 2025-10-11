package com.example.lincride.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.ui.theme.LincColors

/**
 * Route indicator widget specifically for RiderActionBottomSheet
 * Shows a green circle at the top with a dashed line below
 */
@Composable
fun RiderActionRouteInfo(
    pickupLabel: String,
    pickupAddress: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Range indicator column
        Image(
            painter = painterResource(id = com.example.lincride.R.drawable.range_2),
            contentDescription = "Route progress Icon",
        )
        
        // Pickup point details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pickupLabel,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = LincColors.textSecondary
            )
            Text(
                text = pickupAddress,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                color = LincColors.textPrimary
            )
        }
    }
}
