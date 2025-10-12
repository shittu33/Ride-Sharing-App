package com.example.lincride.ui.widget.bottom_sheet

import ShareRideInfoButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.*
import com.example.lincride.viewModel.RideSimulationViewModel

@Composable
fun HeadingToDestinationBottomSheet(
    viewModel: RideSimulationViewModel,
    modifier: Modifier = Modifier
) {
    // Observe the progress from ViewModel
    val progress by viewModel.carMovementProgress.collectAsState()

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
            // Header with destination info
            DestinationHeaderWidget(
                destinationName = "Community Road",
                passengerName = "Nneka Chukwu",
                passengerImageRes = R.drawable.avatar_4
            )

            Spacer(modifier = Modifier.height(8.dp))
            // Progress bar with green color
            DestinationProgressBarWidget(
                progress = progress,
                progressColor = LincColors.secondaryVariant, // Green progress
                trackColor = LincColors.strokeVariant,
                endIconColor = LincColors.textPrimary
            )

            // Multi-stop route section
            MultiStopRouteWidget(
                startingPoint = "Ladipo Oluwole Street",
                destination = "Community Road",
                dropOffStops = listOf(
                    RouteStop(
                        label = "Drop-off 1",
                        address = "Community Road",
                        labelColor = LincColors.secondaryVariant2, // Green
                        isItalic = true,
                        avatarRes = R.drawable.avatar_3,
                        borderColor = LincColors.secondaryVariant
                    ),
                    RouteStop(
                        label = "Drop-off 2",
                        address = "Community Road",
                        labelColor = LincColors.secondaryYellowVariant, // Orange
                        isItalic = true,
                        avatarRes = R.drawable.avatar_4,
                        borderColor = LincColors.secondaryYellowVariant
                    )
                ),
                routeInfo = "Through Aromire Str.",
                etaMinutes = "4 min"
            )

            // Ride stats section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
            ) {
                // Top border for stats section
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .align(Alignment.TopCenter),
                    color = LincColors.stroke,
                    thickness = 0.4.dp
                )

                RideStatsWidget(
                    availableSeats = "1",
                    acceptedPassengers = "1",
                    passengerImages = listOf(
                        R.drawable.avatar_3,
                        R.drawable.avatar_4
                    ),
                    additionalCount = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(47.dp)
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 8.dp, top = 6.dp)
                )
            }

            // Share button
            ShareRideInfoButton()
            // Bottom padding for home indicator
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}
