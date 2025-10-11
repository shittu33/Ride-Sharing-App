package com.example.lincride.ui.widget

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.ui.theme.LincColors

@Composable
fun RideStatsWidget(
    availableSeats: String,
    acceptedPassengers: String,
    passengerImages: List<Int>,
    additionalCount: Int = 0,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Available seats
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1.1f),
                verticalArrangement = Arrangement.spacedBy((2).dp)
            ) {
                Text(
                    text = "Available",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp,),
                    color = LincColors.textSecondary
                )
                Text(
                    text = "seats",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = LincColors.textSecondary
                )
            }


            Text(
                modifier = Modifier.weight(0.5f),
                text = availableSeats,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                color = LincColors.textPrimary
            )


            Column(
                modifier = Modifier.weight(0.8f),
                verticalArrangement = Arrangement.spacedBy((2).dp)
            ) {
                Text(
                    text = "Passengers",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = LincColors.textSecondary
                )
                Text(
                    text = "accepted",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = LincColors.textSecondary
                )
            }

            // Passenger avatars
            Row(
                modifier = Modifier.weight(0.8f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                passengerImages.take(2).forEach { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Passenger",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                if (additionalCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color(0xFFF0F0F0), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+$additionalCount",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                            color = LincColors.textPrimary
                        )
                    }
                }
            }
        }
    }
}
