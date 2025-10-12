package com.example.lincride.ui.widget

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer


/**
 * Animated car marker with ripple effect and directional beam
 */
@Composable
@GoogleMapComposable
fun AnimatedCarMarker(
    position: LatLng,
    rotation: Float = 0f, // Bearing/rotation in degrees (0 = North, 90 = East, etc.)
    title: String = "You",
    carIconRes: Int,
    showRipple: Boolean = true,
    showBeam: Boolean = true,
    showLabel: Boolean = false,
    onMarkerClick: () -> Boolean = { false }
) {
    // Create marker state and update position when it changes
    val markerState = rememberMarkerState(position = position)
    
    // Update marker position whenever position changes
    LaunchedEffect(position) {
        markerState.position = position
    }

    // Create custom marker content using MarkerComposable
    MarkerComposable(
        state = markerState,
        title = title,
        rotation = rotation,
        anchor = Offset(0.5f, 0.5f),
        onClick = { onMarkerClick() }
    ) {
        Box(
            modifier = Modifier.size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            // Layer 1: Directional beam (behind everything)
            if (showBeam) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawDirectionalBeam()
                }
            }

            // Layer 2: Animated ripples
            if (showRipple) {
                RiderLocationRipple()
            }


            // Layer 4: Car icon (on top)
            // Note: No additional rotation needed - MarkerComposable already rotates everything
            Box(
                modifier = Modifier.size(100.dp).padding(bottom = 20.dp ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = carIconRes),
                    contentDescription = "Driver Location",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

/**
 * Draws the directional beam/light rays in front of the car
 * Note: The beam is drawn pointing upward (north). The MarkerComposable's rotation parameter
 * will rotate the entire marker (including this beam) to point in the correct direction.
 */
private fun DrawScope.drawDirectionalBeam() {
    val center = Offset(size.width / 2, size.height / 2)

    // Create a wide fan-shaped beam pointing forward (upward when not rotated)
    val beamLength = 80.dp.toPx()

    // Create path for the beam shape (pointing upward/north by default)
    val beamPath = Path().apply {
        // Start at car position
        moveTo(center.x, center.y)
        
        // Left edge of beam (spread left)
        val leftEdgeX = center.x - beamLength * 0.5f
        val leftEdgeY = center.y - beamLength * 1.2f
        lineTo(leftEdgeX, leftEdgeY)
        
        // Top center of beam (furthest point)
        val topCenterX = center.x
        val topCenterY = center.y - beamLength * 1.5f
        lineTo(topCenterX, topCenterY)
        
        // Right edge of beam (spread right)
        val rightEdgeX = center.x + beamLength * 0.5f
        val rightEdgeY = center.y - beamLength * 1.2f
        lineTo(rightEdgeX, rightEdgeY)
        
        // Close back to center
        close()
    }

    // Create gradient that fades from center outward
    val beamGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6B7FFF).copy(alpha = 1f), // Blue-purple at base
            Color(0xFF6B7FFF).copy(alpha = 0.7f), // Blue-purple at base
            Color(0xFF6B7FFF).copy(alpha = 0.6f), // Blue-purple at base
            Color(0xFF8B9FFF).copy(alpha = 0.2f), // Lighter blue in middle
            Color(0xFF8B9FFF).copy(alpha = 0.001f), // Lighter blue in middle
            Color(0xFFB0C0FF).copy(alpha = 0.0001f), // Very light at edges
            Color.Transparent // Fade to transparent
        ),
        startY = center.y,
        endY = center.y - beamLength * 1.5f
    )

    // Draw the beam (no rotation here - MarkerComposable handles that)
    drawPath(
        path = beamPath,
        brush = beamGradient,
        style = androidx.compose.ui.graphics.drawscope.Fill
    )
}


@Composable
fun RiderLocationRipple(
    pulseColor: Color = Color(0xFF5B68F3).copy(alpha = 0.15f), // Blue with opacity
    durationMillis: Int = 1500 // Speed of the pulse
) {
    Box(
        modifier = Modifier.size(70.dp), // Container large enough for the max scale
        contentAlignment = Alignment.Center
    ) {
        // --- OUTER GLOW/RIPPLE ---
        // Apply the animated scale and alpha via graphicsLayer
        Canvas(
            modifier = Modifier
                .size(70.dp) // Base size for the glow
                .graphicsLayer(
                    scaleX = 0.8f,
                    scaleY = 0.8f,
                    alpha = 0.8f
                )
        ) {
            // Draw the colored circle for the pulse
            drawCircle(
                color = pulseColor,
                radius = size.minDimension / 2
            )
        }

        // --- CENTRAL ELEMENT (Solid Circle + White Border) ---
        Box(
            modifier = Modifier
                .size(30.dp) // Overall size of the center element
                .clip(CircleShape)
                .background(Color.White) // White border background
                .padding(4.dp) // Padding creates the white border thickness
                .clip(CircleShape)
                .background(Color(0xFF5B68F3)) // Blue center
        )
    }
}

/**
 * Alternative: Ground overlay approach for better performance with many markers
 */
@Composable
@GoogleMapComposable
fun GroundOverlayCarMarker(
    position: LatLng,
    rotation: Float = 0f,
    carIcon: BitmapDescriptor,
    width: Float = 100f,
    height: Float = 100f
) {
    GroundOverlay(
        position = GroundOverlayPosition.create(position, width, height),
        image = carIcon,
        bearing = rotation,
        transparency = 0f,
        visible = true,
        zIndex = 1f
    )
}
