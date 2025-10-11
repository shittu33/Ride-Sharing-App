package com.example.lincride.ui.widget.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.*
import com.example.lincride.viewModel.RideSimulationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Bottom sheet shown during "Rider Action" state
 * User can swipe to indicate "Didn't show" or "Picked up"
 * Based on Figma design: 14.2.1 - rider is arriving
 */
@Composable
fun RiderActionBottomSheet(
    viewModel: RideSimulationViewModel,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(Unit) {
        viewModel.viewModelScope.launch {
            delay(5 * 1000)
            viewModel.startDrivingToDestination()
        }
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
            // Title section with waiting time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rider is arriving...",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    ),
                    color = LincColors.textPrimary
                )
                
                // Waiting time widget
                WaitingTimeWidget(
                    waitingTime = "04:45"
                )
            }
            
            // Trip details section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Rider info card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = LincColors.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 2.dp)
                ) {
                    // Label
                    Text(
                        text = "To Pick up",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                        color = LincColors.textPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    // Rider info
                    RiderInfoWidget(
                        riderName = "Nneka Chukwu",
                        riderRating = "4.7",
                        riderImageRes = R.drawable.avatar_4, // Different rider image
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }

                HorizontalDivider( color = Color(0xFFD1D1D1), thickness = 1.dp)


                // Route info
                RiderActionRouteInfo(
                    pickupLabel = "Pick-up point",
                    pickupAddress = "Ladipo Oluwole Street",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Swipeable action bar
            SwipeableActionBar(
                onDidntShow = {
                    viewModel.startDrivingToDestination()
                },
                onPickedUp = {
                    viewModel.startDrivingToDestination()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
            )

            RideStatsWidget(
                availableSeats = "1",
                acceptedPassengers = "1",
                passengerImages = listOf(
                    R.drawable.avatar_2,
                    R.drawable.avatar_3,
                ),
                additionalCount = 1,
                modifier = Modifier.padding(horizontal = 12.dp).padding(bottom =10.dp, top = 8.dp)
            )

            // Stats section
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().height(6.dp),
                thickness = 0.4.dp,
                color = Color(0xFFD1D1D1)
            )

            // Share button - outlined style
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp, top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = { /* Share ride info */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF383838)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = Color(0xFFD1D1D1)
                    ),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(
                        text = "Share Ride Info",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
