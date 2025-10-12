package com.example.lincride.ui.screen

import LincDragHandle
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.BottomSheetBanner
import com.example.lincride.ui.widget.MapView
import com.example.lincride.ui.widget.StopNewRequestsButton
import com.example.lincride.ui.widget.bottom_sheet.HeadingToDestinationBottomSheet
import com.example.lincride.ui.widget.bottom_sheet.OfferRideBottomSheet
import com.example.lincride.ui.widget.bottom_sheet.RideModeBottomSheet
import com.example.lincride.ui.widget.bottom_sheet.RiderActionBottomSheet
import com.example.lincride.ui.widget.overlay.TripEndedOverlay
import com.example.lincride.viewModel.RideSimulationViewModel
import com.example.lincride.viewModel.RideState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: RideSimulationViewModel
) {
    val rideState by viewModel.rideState.collectAsState()
    val density = LocalDensity.current
    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded,
            skipHiddenState = true
        )
    )

    val sheetBackgroundColor = LincColors.surface

    // Calculate visible bottom sheet height for map padding
    val sheetVisibleHeight by remember {
        derivedStateOf {
            try {
                val offsetPx = scaffoldState.bottomSheetState.requireOffset()
                val screenHeightPx = with(density) { screenHeightDp.toPx() }
                val visibleHeightPx = screenHeightPx - offsetPx
                with(density) { visibleHeightPx.toDp() }
            } catch (e: IllegalStateException) {
                0.dp // Sheet not initialized yet
            }
        }
    }

    // Track when bottom sheet is moving
    val isBottomSheetMoving by remember {
        derivedStateOf {
            scaffoldState.bottomSheetState.currentValue != scaffoldState.bottomSheetState.targetValue
        }
    }

    // Reset bottom sheet to expanded when returning to initial state
    LaunchedEffect(rideState) {
        if (!scaffoldState.bottomSheetState.hasExpandedState) {
            scaffoldState.bottomSheetState.expand()
        }
    }



    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            scaffoldState = scaffoldState,
            sheetContainerColor = sheetBackgroundColor,
            sheetShadowElevation = 8.dp,
            sheetDragHandle = {
//                if (rideState == RideState.Initial)
//                    BottomSheetBanner(
//                        iconRes = R.drawable.ic_campaign, // Placeholder - replace with actual ticket-discount icon
//                        count = "1",
//                        label = "Active campaign",
//                        modifier = Modifier.padding(top = 0.dp, bottom = 6.dp)
//                    ) else
                    LincDragHandle()
            },
            sheetContent = {
                // Animated bottom sheet content with smooth transitions
                AnimatedContent(
                    targetState = rideState,
                    transitionSpec = {
                        // Slide in from bottom with fade
                        slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            ),
                            initialOffsetY = { fullHeight -> fullHeight / 3 }
                        ) + fadeIn(
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        ) togetherWith slideOutVertically(
                            animationSpec = tween(200),
                            targetOffsetY = { fullHeight -> -fullHeight / 4 }
                        ) + fadeOut(
                            animationSpec = tween(200)
                        )
                    },
                    label = "bottom_sheet_content"
                ) { currentState ->
                    when (currentState) {
                        is RideState.Initial -> {
                            RideModeBottomSheet(viewModel)
                        }

                        is RideState.DrivingToPickup -> {
                            OfferRideBottomSheet(viewModel = viewModel)
                        }

                        is RideState.RiderAction -> {
                            RiderActionBottomSheet(viewModel = viewModel)
                        }

                        is RideState.DrivingToDestination -> {
                            HeadingToDestinationBottomSheet(viewModel = viewModel)
                        }

                        else -> {
                            // Empty placeholder for other states
                            Box(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        ) {
            // Map view
            MapView(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                viewModel = viewModel,
                bottomPadding = sheetVisibleHeight,
                isBottomSheetMoving = isBottomSheetMoving
            )
        }
    }
}

