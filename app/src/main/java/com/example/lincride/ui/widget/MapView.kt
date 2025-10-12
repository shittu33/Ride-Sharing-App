package com.example.lincride.ui.widget

import android.Manifest
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.viewModelScope
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.viewModel.RideSimulationViewModel
import com.example.lincride.viewModel.RideState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    viewModel: RideSimulationViewModel,
    bottomPadding: Dp = 0.dp,
    isBottomSheetMoving: Boolean = false,
) {
    val rideState by viewModel.rideState.collectAsState()

    val showCampaignBanner = rideState == RideState.Initial

    val density = LocalDensity.current

    // Default location (e.g., Lagos, Nigeria or a central location)
    val defaultLocation = LatLng(6.6111, 3.3645) // Lagos coordinates

    // Pickup and destination locations (example - replace with actual data from ViewModel)
    val pickupLocation = remember { LatLng(6.6095, 3.3685) }
    val destinationLocation = remember { LatLng(6.6035, 3.3695) }

    // Driver location and bearing state
    var driverLocation by remember { mutableStateOf(defaultLocation) }
    var driverBearing by remember { mutableFloatStateOf(0f) }

    var contentBottomPadding by remember { mutableStateOf(bottomPadding) }

    LaunchedEffect(bottomPadding, rideState) {
        contentBottomPadding = if (rideState is RideState.Initial) {
            bottomPadding + 44.dp
        } else {
            bottomPadding
        }
    }

    // Calculate initial bearing to pickup
    LaunchedEffect(Unit) {
        driverBearing = calculateBearing(defaultLocation, pickupLocation)
    }

    // Camera position state
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                defaultLocation.latitude - 0.003,
                defaultLocation.longitude + 0.003
            ), 16f
        )
    }

    // Location permission state
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )


    // Map UI settings
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted,
                mapType = MapType.NORMAL,
                isBuildingEnabled = true,
                isIndoorEnabled = false,
                isTrafficEnabled = false,
                minZoomPreference = 10f,
                maxZoomPreference = 20f,
                mapStyleOptions = MapStyleOptions(mapStyleJson)
            )
        )
    }

    var uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false, // Disable default button
                compassEnabled = false,
            )
        )
    }

    // Request location permission on first composition
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    // Update map properties when permission is granted
    LaunchedEffect(locationPermissionState.status.isGranted) {
        mapProperties = mapProperties.copy(
            isMyLocationEnabled = locationPermissionState.status.isGranted
        )
    }

    LaunchedEffect(rideState) {
        if (showCampaignBanner) {
            driverLocation = defaultLocation
            driverBearing = calculateBearing(defaultLocation, pickupLocation)
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(defaultLocation, 16f)
                ),
                durationMs = 800
            )
        }
    }

    // Camera adjustment effect for bottom sheet movement
    CameraAdjustmentEffect(
        camera = cameraPositionState,
        isBottomSheetMoving = isBottomSheetMoving,
        bottomPadding = contentBottomPadding,
        density = density
    )

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            contentPadding = PaddingValues(bottom = contentBottomPadding.coerceAtLeast(0.dp))
        ) {
            // Add markers based on ride state
            when (rideState) {
                is RideState.Initial -> {
                    // Reset camera to default position

                    // Show driver location with animated marker
                    AnimatedCarMarker(
                        position = driverLocation,
                        rotation = driverBearing,
                        title = "You",
                        carIconRes = R.drawable.car,
                        showLabel = true,
                        showRipple = true,
                        showBeam = true
                    )
                }


                is RideState.DrivingToPickup -> {
                    // Create smooth route from start to pickup
                    val route = remember(defaultLocation, pickupLocation) {
                        createSmoothRoute(defaultLocation, pickupLocation, 60)
                    }

                    // Animate car along the route and get remaining route for shrinking polyline
                    val (animatedPosition, animatedBearing, remainingRoute) = animateCarAlongRoute(
                        route = route,
                        isAnimating = true,
                        durationMs = 8000L, // 8 seconds total,
                        viewModel = viewModel,
                        onComplete = { animatedPosition, _ ->
                            if (!viewModel.isDrivingToPickupCancelled) {
                                driverLocation = animatedPosition
                                viewModel.showConfirmPickupBottomSheet()
                            }
                        }
                    )

                    // Calculate and save progress to view model
                    LaunchedEffect(animatedPosition, remainingRoute) {
                        // Calculate progress based on remaining route length
                        val totalDistance = route.size.toFloat()
                        val remainingDistance = remainingRoute.size.toFloat()
                        val progress = if (totalDistance > 0) {
                            1f - (remainingDistance / totalDistance)
                        } else {
                            0f
                        }
                        if (!viewModel.isDrivingToPickupCancelled) {
                            viewModel.updateCarMovementProgress(progress)
                        }
                    }


                    // Draw shrinking route polyline (only remaining path)
                    RoutePolyline(
                        points = remainingRoute,
                        color = Color(0xFFD97B2E),
                        width = 12f
                    )

                    // Show pickup marker with ripple animation
                    PickupLocationMarker(
                        position = pickupLocation,
                        title = "Pickup Location"
                    )

                    // Render moving car with beam during movement
                    AnimatedCarMarker(
                        position = animatedPosition,
                        rotation = animatedBearing,
                        title = "You",
                        carIconRes = R.drawable.car,
                        showRipple = false,
                        showBeam = false
                    )
                }

                is RideState.PickupConfirmation -> {
                    // Calculate bearing to destination for proper car orientation
                    LaunchedEffect(Unit) {
                        driverBearing = calculateBearing(pickupLocation, destinationLocation)
                    }

                    // Show route to destination
                    val routeToDestination = remember(pickupLocation, destinationLocation) {
                        createSmoothRoute(pickupLocation, destinationLocation, 100)
                    }

                    RoutePolyline(
                        points = routeToDestination,
                        color = Color(0xFFD97B2E),
                        width = 12f
                    )

                    // Car stationary at pickup location
                    AnimatedCarMarker(
                        position = pickupLocation,
                        rotation = driverBearing,
                        title = "You",
                        carIconRes = R.drawable.car,
                        showRipple = true,
                        showBeam = false
                    )

                    // Show destination marker
                    DestinationMarker(
                        position = destinationLocation,
                        title = "Destination"
                    )
                }

                is RideState.DrivingToDestination -> {
                    // Create smooth route from pickup to destination
                    val routeToDestination = remember(pickupLocation, destinationLocation) {
                        createSmoothRoute(pickupLocation, destinationLocation, 50)
                    }

                    // Animate car along the route to destination and get remaining route
                    val (animatedPosition, animatedBearing, remainingRoute) = animateCarAlongRoute(
                        route = routeToDestination,
                        isAnimating = true,
                        durationMs = 10000L, // 10 seconds for longer route
                        viewModel = viewModel,
                        onComplete = { animatedPosition, _ ->
                            if (!viewModel.isDrivingToDestinationCancelled) {
                                driverLocation = animatedPosition
                                viewModel.endTrip()
                            }
                        }
                    )

                    // Calculate and save progress to view model
                    LaunchedEffect(animatedPosition, remainingRoute) {
                        // Calculate progress based on remaining route length
                        val totalDistance = routeToDestination.size.toFloat()
                        val remainingDistance = remainingRoute.size.toFloat()
                        val progress = if (totalDistance > 0) {
                            1f - (remainingDistance / totalDistance)
                        } else {
                            0f
                        }
                        if (!viewModel.isDrivingToDestinationCancelled) {
                            viewModel.updateCarMovementProgress(progress)
                        }
                    }

                    LaunchedEffect(Unit) {
                        if (!viewModel.isDrivingToDestinationCancelled) {
                            delay(700) // Wait before showing bottom sheet
                            // Offset camera slightly south to keep car visible above bottom sheet
                            val offsetPosition = LatLng(
                                animatedPosition.latitude - 0.003, // Move camera down slightly
                                animatedPosition.longitude
                            )
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newCameraPosition(
                                    CameraPosition.fromLatLngZoom(offsetPosition, 16f)
                                ),
                                durationMs = 700
                            )
                        }
                    }

                    // Draw shrinking route polyline (only remaining path)
                    RoutePolyline(
                        points = remainingRoute,
                        color = Color(0xFFD97B2E),
                        width = 12f
                    )

                    // Show destination marker
                    DestinationMarker(
                        position = destinationLocation,
                        title = "Destination"
                    )

                    // Render moving car with beam
                    AnimatedCarMarker(
                        position = animatedPosition,
                        rotation = animatedBearing,
                        title = "You",
                        carIconRes = R.drawable.car,
                        showRipple = false,
                        showBeam = false
                    )
                }

                is RideState.TripEnded -> {
                    // Car at destination (no animation)
                    AnimatedCarMarker(
                        position = driverLocation,
                        rotation = driverBearing,
                        title = "You",
                        carIconRes = R.drawable.car,
                        showRipple = false,
                        showBeam = false
                    )
                }

                RideState.RiderAction -> {
                    AnimatedCarMarker(
                        position = driverLocation,
                        rotation = driverBearing,
                        title = "You",
                        carIconRes = R.drawable.car,
                        showRipple = false,
                        showBeam = false
                    )
                }
            }

        }
        // Overlay controls
        MapOverLayView(
            modifier = Modifier.align(Alignment.TopEnd),
            viewModel = viewModel,
            showCampaignBanner = showCampaignBanner,
            bottomPadding = bottomPadding,
            cameraPositionState, contentBottomPadding, driverLocation
        )

    }
}

@Composable
private fun BoxScope.MapOverLayView(
    modifier: Modifier = Modifier,
    viewModel: RideSimulationViewModel,
    showCampaignBanner: Boolean,
    bottomPadding: Dp,
    cameraPositionState: CameraPositionState,
    contentBottomPadding: Dp,
    driverLocation: LatLng
) {
    val rideState by viewModel.rideState.collectAsState()

    if (rideState is RideState.RiderAction ||
        rideState is RideState.DrivingToPickup ||
        rideState is RideState.DrivingToDestination
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StopNewRequestsButton(
                onClick = {
                    // Handle stop new requests action
                    // This could reset to initial state or pause ride requests
                }
            )
        }
    }

    // Emergency button - always visible above bottom sheet
    EmergencyButton(
        onClick = { /* Handle emergency click */ },
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(bottom = contentBottomPadding + 46.dp, start = 2.dp)
    )

    if (showCampaignBanner)
        BottomSheetBanner(
            iconRes = R.drawable.ic_campaign, // Placeholder - replace with actual ticket-discount icon
            count = "1",
            label = "Active campaign",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 0.dp, bottom = max(bottomPadding - 26.dp, 0.dp))
        )

    Column(
        Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = contentBottomPadding + 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        //Round Icon Button to reset simulation
        SimulationActionButton(
            onClick = { viewModel.startNextSimulation() },
            modifier = Modifier.padding(start = 8.dp),
            icon = R.drawable.ic_chevron_double_right,
            iconSize = 44.dp,
            tile = "Reset Simulation"
        )

        //Round Icon Button to move to next simulation
        SimulationActionButton(
            onClick = { viewModel.resetSimulation() },
            modifier = Modifier,
            icon = android.R.drawable.ic_menu_rotate,
            tile = "Next Simulation"
        )

        // Custom My Location Button
        SimulationActionButton(
            onClick = {
                // Center camera on user's current location
                // You can get the location from FusedLocationProvider or use the current driverLocation
                cameraPositionState.position.target.let { currentTarget ->
                    viewModel.viewModelScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(driverLocation, 16f),
                            durationMs = 500
                        )
                    }
                }
            },
            modifier = Modifier,
            icon = R.drawable.ic_send,
            tile = "My Location"
        )
    }


}


// Custom map style matching Figma design
val mapStyleJson = """
    [
      {
        "elementType": "geometry",
        "stylers": [{"color": "#f5f5f5"}]
      },
      {
        "elementType": "labels.icon",
        "stylers": [{"visibility": "off"}]
      },
      {
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#616161"}]
      },
      {
        "elementType": "labels.text.stroke",
        "stylers": [{"color": "#f5f5f5"}]
      },
      {
        "featureType": "administrative.land_parcel",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#bdbdbd"}]
      },
      {
        "featureType": "poi",
        "elementType": "geometry",
        "stylers": [{"color": "#eeeeee"}]
      },
      {
        "featureType": "poi",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#757575"}]
      },
      {
        "featureType": "poi.park",
        "elementType": "geometry",
        "stylers": [{"color": "#e5e5e5"}]
      },
      {
        "featureType": "poi.park",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#9e9e9e"}]
      },
      {
        "featureType": "road",
        "elementType": "geometry",
        "stylers": [{"color": "#ffffff"}]
      },
      {
        "featureType": "road.arterial",
        "elementType": "geometry",
        "stylers": [{"color": "#ffffff"}]  // ← Add this for arterial roads
      },
      {
        "featureType": "road.arterial",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#757575"}]
      },
      {
        "featureType": "road.highway",
        "elementType": "geometry",
        "stylers": [{"color": "#ffffff"}]
      },
      {
        "featureType": "road.highway",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#616161"}]
      },
      {
        "featureType": "road.local",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#9e9e9e"}]
      },
      {
        "featureType": "transit.line",
        "elementType": "geometry",
        "stylers": [{"color": "#e5e5e5"}]
      },
      {
        "featureType": "transit.station",
        "elementType": "geometry",
        "stylers": [{"color": "#eeeeee"}]
      },
      {
        "featureType": "water",
        "elementType": "geometry",
        "stylers": [{"color": "#ffffff"}]
      },
      {
        "featureType": "water",
        "elementType": "labels.text.fill",
        "stylers": [{"color": "#9e9e9e"}]
      }
    ]
    """.trimIndent()

/**
 * Adjusts camera position when bottom sheet moves to keep the map centered properly
 */
@Composable
private fun CameraAdjustmentEffect(
    camera: CameraPositionState,
    isBottomSheetMoving: Boolean,
    bottomPadding: Dp,
    density: Density
) {
    // Track the last camera location before bottom sheet movement
    var cameraLocation by remember { mutableStateOf(camera.position.target) }

    // Update camera location when user manually moves the map
    LaunchedEffect(camera.isMoving, camera.cameraMoveStartedReason) {
        if (!camera.isMoving && camera.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
            cameraLocation = camera.position.target
        }
    }

    // Track if camera has been initialized with initial padding
    var isCameraInitialized by remember { mutableStateOf(false) }

    // Adjust camera when bottom sheet stops moving
    LaunchedEffect(isBottomSheetMoving, bottomPadding) {
        if (isBottomSheetMoving) return@LaunchedEffect

        if (!isCameraInitialized) {
            // Initial adjustment: Shift camera down by half the bottom padding
            // This compensates for the map thinking the center is higher than it actually is
            isCameraInitialized = true
            val verticalShiftPx = with(density) { bottomPadding.toPx() / 2 }
            val update =
                CameraUpdateFactory.scrollBy(0f, verticalShiftPx)
            camera.animate(update, durationMs = 300)
        } else {
            // Subsequent adjustments: Return to the saved camera location
            val update = CameraUpdateFactory.newLatLng(cameraLocation)
            camera.animate(update, durationMs = 300)
        }
    }
}

@Composable
private fun EmergencyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = LincColors.primary // Red color for emergency
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_shield),
            contentDescription = "Emergency",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Emergency",
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SimulationActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconSize: Dp = 28.dp,
    tile: String,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            TooltipAnchorPosition.Above
        ),
        tooltip = {
            RichTooltip(
                modifier = Modifier.height(50.dp),
                title = { Text(tile) },
                action = {
                },
                caretShape = TooltipDefaults.caretShape(DpSize(28.dp, 20.dp)),
            ) { }
        },
        state = rememberTooltipState(isPersistent = false),
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp),
            containerColor = LincColors.surface,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 2.dp,
                pressedElevation = 8.dp
            ),
        ) {


            Icon(
                painter = painterResource(id = icon),
                contentDescription = tile,
                modifier = modifier
                    .size(iconSize)
            )
        }
    }

}
