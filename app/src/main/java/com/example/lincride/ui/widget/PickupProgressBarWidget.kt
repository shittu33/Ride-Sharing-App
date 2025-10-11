package com.example.lincride.ui.widget

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun PickupProgressBarWidget(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val deepBlue = Color(0xFF1F53B5)
    
    // Animate progress with uniform linear speed - no easing for instant response
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(
            delayMillis = 10,
            durationMillis = 100, // Very fast, minimal delay
            easing = LinearEasing // Uniform speed, no acceleration/deceleration
        ),
        label = "progress_animation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 0.dp, bottom = 8.dp)
    ) {
        // Progress track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(
                    color = Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(4.dp)
                )
                .align(Alignment.Center)
        ) {
            // Filled progress (inverted - gray fills from right to left as car progresses)
            Box(
                modifier = Modifier
                    .fillMaxWidth((1f - animatedProgress).coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF9EC0FF),
                                deepBlue
                            )
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .align(Alignment.CenterEnd) // Align to right so it shrinks from left
            )
        }

        // Row containing car and destination icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.weight(1f))
            // Destination icon at the end
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .border(
                        width = 4.dp,
                        color = LincColors.primary.copy(0.8f),
                        shape = CircleShape
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = deepBlue, // Light green background
                            shape = CircleShape
                        )
                        .align(Alignment.Center)
                )
            }
        }

        // Car icon at the start moving along the progress
        // Car icon moving smoothly along the progress
        Image(
            painter = painterResource(id = R.drawable.car_horizontal),
            contentDescription = "Car",
            modifier = Modifier
                .offset(
                    x = (animatedProgress * 340).dp
                ) // Use same animated value for synchronized movement
                .size(64.dp, 30.dp)
        )
    }
}