package com.example.lincride.ui.widget

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lincride.ui.theme.LincColors

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun DestinationProgressBarWidget(
    progress: Float,
    modifier: Modifier = Modifier,
    progressColor: Color = Color(0xFF60B527), // Default green color
    trackColor: Color = LincColors.strokeVariant,
    endIconColor: Color = Color(0xFF2A2A2A)
) {
    // Animate progress with uniform linear speed
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(
            delayMillis = 0,
            durationMillis = 100,
            easing = LinearEasing
        ),
        label = "progress_animation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 8.dp)
    ) {
        // Progress track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    color = progressColor,
//                    shape = RoundedCornerShape(4.dp)
                )
                .align(Alignment.Center)
        ) {
            // Filled progress (from left to right)
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(
                        color = trackColor,
                        shape = RoundedCornerShape(0.dp)
                    )
            )
        }


        // Car icon moving along the progress
        Image(
            painter = painterResource(id = com.example.lincride.R.drawable.car_horizontal),
            contentDescription = "Car",
            modifier = Modifier
                .offset(
                    x = (animatedProgress * 360).dp
                )
                .size(70.dp, 30.dp)
        )
    }
}
