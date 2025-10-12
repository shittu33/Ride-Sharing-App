package com.example.lincride.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lincride.R

@Composable
fun CO2StatsWidget(
    co2Amount: String,
    periodText: String,
    progress: Float = 1f,
    modifier: Modifier = Modifier
) {
    // Animate progress from 0 to target value
    val animatedProgress by animateFloatAsState(
        targetValue =  progress.coerceIn(0f, 1f),
        animationSpec = tween(
            delayMillis = 1000,
            durationMillis = 2000, easing = androidx.compose.animation.core.LinearEasing),
        label = "co2_progress"
    )
    
    // Calculate the angle for the current progress (starts at -90°, goes clockwise)
    val currentAngle = -90f + (45f * animatedProgress)
    
    // Calculate the position of the check icon on the circle
    val radius = 57.145.dp // Half of 114.29dp
    val angleInRadians = Math.toRadians(currentAngle.toDouble())
    val iconOffsetX = (radius.value * kotlin.math.cos(angleInRadians)).dp
    val iconOffsetY = (radius.value * kotlin.math.sin(angleInRadians)).dp
    
    Box(
        modifier = modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circle with gradient
        Canvas(modifier = Modifier.size(120.dp)) {
            // Background gradient circle
            drawCircle(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF198C58),
                        Color(0xFF136B43)
                    )
                ),
                radius = size.minDimension / 2
            )
            
            // Progress arc (green stroke) - animated
            drawArc(
                color = Color(0xFF2DFFA0),
                startAngle = -90f,
                sweepAngle = 45f * animatedProgress, // 25%
                useCenter = false,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round),
                topLeft = androidx.compose.ui.geometry.Offset(
                    2.86.dp.toPx(),
                    2.86.dp.toPx()
                ),
                size = androidx.compose.ui.geometry.Size(
                    114.29.dp.toPx(),
                    114.29.dp.toPx()
                )
            )
        }
        
        // Green check icon at the end of the progress arc (animated position)
        Box(
            modifier = Modifier
                .size(16.8.dp)
                .offset(x = iconOffsetX, y = iconOffsetY)
                .background(Color(0xFF2DFFA0), CircleShape)
        )
        
        // Center content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "CO₂",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                color = Color(0xFF29E892)
            )
            Text(
                text = co2Amount,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                color = Color(0xFFF8F8F8)
            )
            Text(
                text = periodText,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Color(0xFF29E892),
                modifier = Modifier.width(71.dp)
            )
        }
    }
}
