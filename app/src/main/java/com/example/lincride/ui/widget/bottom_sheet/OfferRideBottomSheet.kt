package com.example.lincride.ui.widget.bottom_sheet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.*
import com.example.lincride.viewModel.RideSimulationViewModel
import kotlin.math.ceil

@Composable
fun OfferRideBottomSheet(
    viewModel: RideSimulationViewModel,
    modifier: Modifier = Modifier
) {
    // Observe the progress from ViewModel
    val progress by viewModel.carMovementProgress.collectAsState()

    // Calculate remaining time based on actual animation duration (8 seconds = 8000ms)
    val totalAnimationSeconds = 8 // Total animation time in seconds
    val remainingSeconds = ((1f - progress) * totalAnimationSeconds).toInt().coerceAtLeast(0)

    // Convert to minutes and seconds for display
    val remainingMinutes = remainingSeconds / 60
    val remainingSecondsDisplay = remainingSeconds % 60

    val timeText = when {
        remainingSeconds == 0 -> "Arrived"
        remainingMinutes > 0 -> "$remainingMinutes min ${remainingSecondsDisplay}s away"
        else -> "${remainingSecondsDisplay}s away"
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = LincColors.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Title section with time chip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 0.dp)
                    .padding(top = 4.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Get to pickup...",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = LincColors.textPrimary
                )
                
                TimeChipWidget(timeText = timeText)
            }
            
            // Progress bar with car and destination icons
            PickupProgressBarWidget(progress = progress)

            HorizontalDivider(Modifier.height(6.dp), color = LincColors.stroke, thickness = 0.4.dp)

            Spacer(modifier = Modifier.height(6.dp))

            // Main content area with border
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                // "To Pick" card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = LincColors.surface,
                            shape = RoundedCornerShape(16.dp)
                        ).border(
                            width = 4.dp,
                            color = LincColors.primary.copy(0.4f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(top = 6.dp)
                ) {
                    // Label
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp).padding(top = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "To Pick ",
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                            color = LincColors.textPrimary,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    // Rider info
                    RiderInfoWidget(
                        riderName = "Darrell Steward",
                        riderRating = "4.7",
                        riderImageRes = R.drawable.avatar,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                    )
                    
                    // Route progress indicator with dynamic ETA
                    val etaText = when {
                        remainingSeconds == 0 -> "0s"
                        remainingMinutes > 0 -> "${remainingMinutes}m ${remainingSecondsDisplay}s"
                        else -> "${remainingSecondsDisplay}s"
                    }
                    RouteProgressIndicator(
                        pickupLabel = "Pick-up point",
                        pickupAddress = "Ladipo Oluwole Street",
                        etaMinutes = etaText,
                        progress = progress,
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )
                }
            }
            
            // Stats section
            RideStatsWidget(
                availableSeats = "2",
                acceptedPassengers = "2",
                passengerImages = listOf(
                    R.drawable.avatar_2,
                    R.drawable.avatar_3
                ),
                additionalCount = 1,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).padding(bottom = 10.dp, top = 10.dp)
            )
            
            // Share button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /* Share ride info */ },
                    modifier = Modifier
                        .width(240.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F1F1F)
                    ),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(
                        text = "Share Ride Info",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                        color = LincColors.surfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

