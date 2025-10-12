package com.example.lincride.ui.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lincride.ui.theme.LincColors
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.*

/**
 * Draws a route line between car and pickup location
 */
@Composable
@GoogleMapComposable
fun RoutePolyline(
    points: List<LatLng>,
    color: Color = Color(0xFFD97B2E), // Orange/brown color from screenshot
    width: Float = 12f,
    isDashed: Boolean = false,
    zIndex: Float = 0f
) {
    if (points.size < 2) return

    Polyline(
        points = points,
        color = color,
        width = width,
        startCap = RoundCap(),
        endCap = RoundCap(),
        jointType = JointType.ROUND,
        geodesic = true,
        visible = true,
        zIndex = zIndex,
        pattern = if (isDashed) {
            listOf(
                com.google.android.gms.maps.model.Dash(30f),
                com.google.android.gms.maps.model.Gap(20f)
            )
        } else null
    )
}

/**
 * Pickup location marker (green circle with white border and animated ripple)
 */
@Composable
@GoogleMapComposable
fun PickupLocationMarker(
    position: LatLng,
    title: String = "Pickup",
    snippet: String? = null,
    onMarkerClick: () -> Boolean = { false }
) {
    val pointerColor = LincColors.secondary
    val pointerStroke = LincColors.surface
    val pointerRippleColor = LincColors.stroke.copy(alpha = 0.4f)

    MarkerComposable(
        state = rememberMarkerState(position = position),
        title = title,
        snippet = snippet,
        anchor = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
        zIndex = 1f,
        onClick = { onMarkerClick() }
    ) {
        PickUpLocationRipple()
//        androidx.compose.foundation.layout.Box(
//            modifier = Modifier.size(100.dp),
//            contentAlignment = androidx.compose.ui.Alignment.Center
//        ) {
        // Animated ripple circles
//            androidx.compose.foundation.Canvas(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
//                val baseRadius = 12.dp.toPx()
//
//
//                // Main marker - Outer white circle (border)
//                drawCircle(
//                    color = pointerStroke,
//                    radius = baseRadius,
//                    center = center
//                )
//
//                // Inner green circle
//                drawCircle(
//                    color = pointerColor,
//                    radius = baseRadius - 3.dp.toPx(),
//                    center = center
//                )
//            }
//        }
    }
}

/**
 * Destination marker (similar style but different color)
 */
@Composable
@GoogleMapComposable
fun DestinationMarker(
    position: LatLng,
    title: String = "Destination",
    snippet: String? = null,
    onMarkerClick: () -> Boolean = { false }
) {
    MarkerComposable(
        state = rememberMarkerState(position = position),
        title = title,
        snippet = snippet,
        anchor = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
        zIndex = 1f,
        onClick = { onMarkerClick() }
    ) {
        Image(
            painter = androidx.compose.ui.res.painterResource(id = com.example.lincride.R.drawable.ic_dest_marker),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
//        Box(
//            modifier = Modifier
//                .size(30.dp)
//                .offset(y = (-1).dp), // Offset to center the circular part
//            contentAlignment = androidx.compose.ui.Alignment.TopCenter
//        ) {
//            androidx.compose.foundation.Canvas(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                val canvasWidth = size.width
//                val canvasHeight = size.height
//
//                // Main circle dimensions
//                val circleRadius = canvasWidth * 0.4f
//                val circleCenterX = canvasWidth / 2
//                val circleCenterY = circleRadius + 2.dp.toPx()
//
//                // Pin/teardrop dimensions
//                val pinWidth = 8.dp.toPx()
//                val pinHeight = 12.dp.toPx()
//                val pinTop = circleCenterY + circleRadius - 2.dp.toPx()
//
//                // Draw the pin/teardrop shape at the bottom
//                val pinPath = androidx.compose.ui.graphics.Path().apply {
//                    moveTo(circleCenterX, pinTop)
//                    lineTo(circleCenterX - pinWidth / 2, pinTop)
//                    lineTo(circleCenterX, pinTop + pinHeight)
//                    lineTo(circleCenterX + pinWidth / 2, pinTop)
//                    close()
//                }
//
//                drawPath(
//                    path = pinPath,
//                    color = Color(0xFF4A5FC1) // Blue for pin
//                )
//
//                // Draw outer white border circle
//                drawCircle(
//                    color = Color.White,
//                    radius = circleRadius + 3.dp.toPx(),
//                    center = androidx.compose.ui.geometry.Offset(circleCenterX, circleCenterY)
//                )
//
//                // Draw main blue circle
//                drawCircle(
//                    color = Color(0xFF4A5FC1), // Blue for destination
//                    radius = circleRadius,
//                    center = androidx.compose.ui.geometry.Offset(circleCenterX, circleCenterY)
//                )
//
//                // Draw inner white dot
//                drawCircle(
//                    color = Color.White,
//                    radius = circleRadius * 0.35f,
//                    center = androidx.compose.ui.geometry.Offset(circleCenterX, circleCenterY)
//                )
//            }
//        }
    }
}


@Composable
fun PickUpLocationRipple(
    durationMillis: Int = 1500 // Speed of the pulse
) {
    val pointerColor = LincColors.secondary
    val pointerStroke = LincColors.surface
    val pointerRippleColor = LincColors.stroke.copy(alpha = 0.4f)
    Box(
        modifier = Modifier.size(60.dp), // Container large enough for the max scale
        contentAlignment = Alignment.Center
    ) {
        // --- OUTER GLOW/RIPPLE ---
        // Apply the animated scale and alpha via graphicsLayer
        Canvas(
            modifier = Modifier
                .size(60.dp) // Base size for the glow
                .graphicsLayer(
                    scaleX = 0.8f,
                    scaleY = 0.8f,
                    alpha = 0.8f
                )
        ) {
            // Draw the colored circle for the pulse
            drawCircle(
                color = Color.Black.copy(green = 0.2f, alpha = 0.2f),
                radius = size.minDimension / 2
            )
        }

        // --- CENTRAL ELEMENT (Solid Circle + White Border) ---
        Box(
            modifier = Modifier
                .size(25.dp) // Overall size of the center element
                .clip(CircleShape)
                .background(Color.White) // White border background
                .padding(4.dp) // Padding creates the white border thickness
                .clip(CircleShape)
                .background(pointerColor) // Blue center
        )
    }
}


/**
 * Creates a smooth route with intermediate points for better visualization
 */
fun createSmoothRoute(
    start: LatLng,
    end: LatLng,
    numIntermediatePoints: Int = 60
): List<LatLng> {
    val route = mutableListOf<LatLng>()

    for (i in 0..numIntermediatePoints) {
        val fraction = i.toFloat() / numIntermediatePoints
        val lat = start.latitude + (end.latitude - start.latitude) * fraction
        val lng = start.longitude + (end.longitude - start.longitude) * fraction
        route.add(LatLng(lat, lng))
    }

    return route
}

/**
 * Calculates bounds that include all points with padding
 */
fun calculateRouteBounds(points: List<LatLng>): com.google.android.gms.maps.model.LatLngBounds {
    val builder = com.google.android.gms.maps.model.LatLngBounds.Builder()
    points.forEach { builder.include(it) }
    return builder.build()
}

