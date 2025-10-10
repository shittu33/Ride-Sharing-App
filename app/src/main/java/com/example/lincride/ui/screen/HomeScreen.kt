package com.example.lincride.ui.screen

import LincDragHandle
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lincride.R
import com.example.lincride.ui.theme.LincColors
import com.example.lincride.ui.widget.BottomSheetBanner
import com.example.lincride.ui.widget.MapView
import com.example.lincride.ui.widget.bottom_sheet.RideModeBottomSheet
import com.example.lincride.viewModel.RideSimulationViewModel
import com.example.lincride.viewModel.RideState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: RideSimulationViewModel) {
    val rideState by viewModel.rideState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded
    ))
    val scope = rememberCoroutineScope()
    val sheetBackgroundColor =
        if (rideState == RideState.Initial) LincColors.secondary else LincColors.surface

    BottomSheetScaffold(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        scaffoldState = scaffoldState,
        sheetContainerColor = sheetBackgroundColor,
        sheetShadowElevation = 8.dp,
        sheetDragHandle = {
            if (rideState == RideState.Initial)
                BottomSheetBanner(
                    iconRes = R.drawable.ic_campaign, // Placeholder - replace with actual ticket-discount icon
                    count = "1",
                    label = "Active campaign",
                    modifier = Modifier.padding(top = 0.dp, bottom = 6.dp)
                ) else
                LincDragHandle()
        },
        sheetContent = {
            // Main bottom sheet content
            when (rideState) {
                is RideState.Initial -> {
                    RideModeBottomSheet(
                        viewModel
                    )
                }

                is RideState.OfferRideBottomSheet -> {
                    Box(Modifier.height(132.dp).fillMaxWidth().background(color = LincColors.stroke)){

                    }
                }

                else -> {}
            }
        }
    ) {
        MapView(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            viewModel = viewModel
        )

    }
}

