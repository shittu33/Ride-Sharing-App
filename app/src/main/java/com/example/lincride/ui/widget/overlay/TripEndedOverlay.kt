package com.example.lincride.ui.widget.overlay

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.CO2StatsWidget
import com.example.lincride.ui.widget.EarningsListWidget
import com.example.lincride.ui.widget.PassengerRatingCard

@Composable
fun TripEndedOverlay(
    visible: Boolean,
    onNewTrip: () -> Unit,
    onEarningsHistory: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Track animation state separately - starts at 0 when visible becomes true
    var animateProgress by remember { mutableStateOf(false) }
    
    // Trigger animation after overlay becomes visible
    LaunchedEffect(visible) {
        if (visible) {
            animateProgress = true
        } else {
            animateProgress = false
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(400)) + slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialOffsetY = { it / 2 }
        ),
        exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
            animationSpec = tween(300),
            targetOffsetY = { it / 2 }
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF136B43),
                            Color(0xFF25D183)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(
                        orientation = Orientation.Vertical,
                        state = rememberScrollState(),
                    )
            ) {
                // Status bar spacing
                Spacer(modifier = Modifier.height(47.dp))

                // Top section with CO2 stats and success message
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .padding(top = 38.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // CO2 Stats Circle
                    CO2StatsWidget(
                        co2Amount = "0.86 kg",
                        periodText = "So far this month",
                        progress = if (animateProgress) 1f else 0f
                    )

                    // Success message
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Trip Complete! Thank You.",
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                            color = Color(0xFFF8F8F8),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Another successful trip, well done!",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                            color = Color(0xFFF0F0F0),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Carbon emission info
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 3.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sun),
                            contentDescription = "Sun icon",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "Carbon Emission Avoided:",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Color(0xFFF0F0F0)
                        )
                        Text(
                            text = "~1.2 km private car equivalent",
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                            color = Color(0xFFF8F8F8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom card with white background
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                ) {
                    // Top banner with gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFF2FCE9),
                                        Color(0xFFFFFFFF)
                                    )
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You helped 4 riders get to their destinations.",
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp),
                            color = Color(0xFF3A711A),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Rate passengers section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 0.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Rate your passengers",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                            color = Color(0xFF2A2A2A),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFFFFFF))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            PassengerRatingCard(
                                name = "Wade Warren",
                                avatarRes = R.drawable.avatar_3,
                                pickupPoint = "Ladipo Oluwole Street",
                                onRateClick = { /* Handle rating */ }
                            )

                            PassengerRatingCard(
                                name = "Brooklyn Simmons",
                                avatarRes = R.drawable.avatar_2,
                                pickupPoint = "Ladipo Oluwole Street",
                                onRateClick = { /* Handle rating */ }
                            )
                        }
                    }

                    // Earnings section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Earnings for This Trip",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                            color = Color(0xFF2A2A2A),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )

                        EarningsListWidget(
                            netEarnings = "₦6,500.00",
                            bonus = "₦500.00",
                            commission = "₦500.00"
                        )
                    }

                    // Action buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = onEarningsHistory,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(32.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFF0F0F0),
                                contentColor = Color(0xFF383838)
                            )
                        ) {
                            Text(
                                text = "Earnings History",
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                                color = Color(0xFF383838)
                            )
                        }

                        Button(
                            onClick = onNewTrip,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(32.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2A2A2A),
                                contentColor = Color(0xFFFFFFFF)
                            )
                        ) {
                            Text(
                                text = "New Trip",
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                                color = Color(0xFFFFFFFF)
                            )
                        }
                    }

                    // Bottom spacing for home indicator
                    Spacer(modifier = Modifier.height(138.dp))
                }
            }

            // Cancel button in top-right corner
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 57.dp, start = 16.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        onCancel()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close",
                    tint = Color(0xFF2A2A2A),
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }
}
