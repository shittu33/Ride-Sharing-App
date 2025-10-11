package com.example.lincride.ui.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Animates car movement along a route with smooth transitions using Compose Animatable
 * Returns State objects that emit values continuously during animation
 * Also returns current position, bearing, and remaining route for polyline shrinking effect
 */
@Composable
fun animateCarAlongRoute(
    route: List<LatLng>,
    isAnimating: Boolean,
    durationMs: Long = 10000L, // Total duration for entire route
    onComplete: (animatedPosition: LatLng, animatedBearing: Float) -> Unit = {_,_->},
): Triple<LatLng, Float, List<LatLng>> {
    // Current position state - these emit values during animation
    val animatedLat = remember { Animatable((route.firstOrNull()?.latitude ?: 0.0).toFloat()) }
    val animatedLng = remember { Animatable((route.firstOrNull()?.longitude ?: 0.0).toFloat()) }
    var currentBearing by remember { mutableFloatStateOf(0f) }
    var currentSegmentIndex by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(isAnimating) {
        if (!isAnimating || route.size < 2) return@LaunchedEffect
        
        // Reset to start
        animatedLat.snapTo(route.first().latitude.toFloat())
        animatedLng.snapTo(route.first().longitude.toFloat())
        currentSegmentIndex = 0
        
        // Calculate total distance for proportional speed
        val totalDistance = calculateTotalDistance(route)
        
        // Animate through each segment
        for (i in 0 until route.size - 1) {
            val start = route[i]
            val end = route[i + 1]
            currentSegmentIndex = i
            
            // Calculate bearing for this segment
            currentBearing = calculateBearing(start, end)
            
            // Calculate segment duration proportional to distance
            val segmentDistance = calculateDistance(start, end)
            val segmentDuration = ((segmentDistance / totalDistance) * durationMs).toLong()
            
            // Animate latitude and longitude in parallel using coroutineScope
            kotlinx.coroutines.coroutineScope {
                launch {
                    animatedLat.animateTo(
                        targetValue = end.latitude.toFloat(),
                        animationSpec = tween(
                            durationMillis = segmentDuration.toInt(),
                            easing = LinearEasing
                        )
                    )
                }
                launch {
                    animatedLng.animateTo(
                        targetValue = end.longitude.toFloat(),
                        animationSpec = tween(
                            durationMillis = segmentDuration.toInt(),
                            easing = LinearEasing
                        )
                    )
                }
            }
        }
        
        onComplete(LatLng(animatedLat.value.toDouble(), animatedLng.value.toDouble()), currentBearing)
    }
    
    // Return current animated values and remaining route
    val currentPosition = LatLng(animatedLat.value.toDouble(), animatedLng.value.toDouble())
    
    // Calculate remaining route (from current position to end)
    val remainingRoute = if (currentSegmentIndex < route.size - 1) {
        listOf(currentPosition) + route.subList(currentSegmentIndex + 1, route.size)
    } else {
        listOf(currentPosition, route.last())
    }
    
    return Triple(currentPosition, currentBearing, remainingRoute)
}

/**
 * Calculate bearing (direction) from one point to another
 * Returns angle in degrees (0 = North, 90 = East, 180 = South, 270 = West)
 */
fun calculateBearing(start: LatLng, end: LatLng): Float {
    val startLat = Math.toRadians(start.latitude)
    val startLng = Math.toRadians(start.longitude)
    val endLat = Math.toRadians(end.latitude)
    val endLng = Math.toRadians(end.longitude)
    
    val dLng = endLng - startLng
    
    val y = sin(dLng) * cos(endLat)
    val x = cos(startLat) * sin(endLat) - sin(startLat) * cos(endLat) * cos(dLng)
    
    var bearing = Math.toDegrees(atan2(y, x))
    bearing = (bearing + 360) % 360 // Normalize to 0-360
    
    return bearing.toFloat()
}

/**
 * Calculate distance between two points in meters
 * Uses Haversine formula
 */
fun calculateDistance(start: LatLng, end: LatLng): Double {
    val earthRadius = 6371000.0 // meters
    
    val lat1 = Math.toRadians(start.latitude)
    val lat2 = Math.toRadians(end.latitude)
    val dLat = Math.toRadians(end.latitude - start.latitude)
    val dLng = Math.toRadians(end.longitude - start.longitude)
    
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1) * cos(lat2) *
            sin(dLng / 2) * sin(dLng / 2)
    
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    
    return earthRadius * c
}

/**
 * Calculate total distance of a route
 */
fun calculateTotalDistance(route: List<LatLng>): Double {
    var totalDistance = 0.0
    for (i in 0 until route.size - 1) {
        totalDistance += calculateDistance(route[i], route[i + 1])
    }
    return totalDistance
}

/**
 * Interpolate between two positions
 */
fun interpolatePosition(start: LatLng, end: LatLng, fraction: Float): LatLng {
    val lat = start.latitude + (end.latitude - start.latitude) * fraction
    val lng = start.longitude + (end.longitude - start.longitude) * fraction
    return LatLng(lat, lng)
}
