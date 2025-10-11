package com.example.lincride.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideSimulationViewModel @Inject constructor() : ViewModel() {

    private val _rideState = MutableStateFlow<RideState>(RideState.Initial)
    val rideState: StateFlow<RideState> = _rideState.asStateFlow()

    private val _rideModeState = MutableStateFlow<RideModeState>(RideModeState.OfferRideState)
    val rideModeState: StateFlow<RideModeState> = _rideModeState.asStateFlow()

    // Progress state for car movement (0f to 1f)
    private val _carMovementProgress = MutableStateFlow(0f)
    val carMovementProgress: StateFlow<Float> = _carMovementProgress.asStateFlow()

    // Update car movement progress
    fun updateCarMovementProgress(progress: Float) {
        _carMovementProgress.value = progress.coerceIn(0f, 1f)
    }

    // Trigger Event 1: Show "Offer a Ride" Bottom Sheet
    private fun showRideModeBottomSheet() {
        _rideState.value = RideState.Initial
    }

    // Trigger Event 2: Show "Offer a Ride" Bottom Sheet
     fun showOfferRideBottomSheet() {
        _rideState.value = RideState.OfferRideBottomSheet
        viewModelScope.launch {
            kotlinx.coroutines.delay(2 * 1000) // Simulate user interaction delay
            startDrivingToPickup()
        }
    }

    // Start driving simulation to pickup (Event 3)
    private fun startDrivingToPickup() {
        _carMovementProgress.value = 0f // Reset progress
        _rideState.value = RideState.DrivingToPickup(0f)
    }
    
    // Confirm arrival at pickup location
    fun onPickupConfirmed() {
        _rideState.value = RideState.PickupConfirmation
    }

    // Trigger Event 4: Show Rider Action Bottom Sheet
    fun showConfirmPickupBottomSheet() {
        _rideState.value = RideState.RiderAction
    }

    // Start driving simulation to destination (Event 5)
    fun startDrivingToDestination() {
        _carMovementProgress.value = 0f // Reset progress
        _rideState.value = RideState.DrivingToDestination(0f)
    }
    
    // End trip - arrival at destination
    fun endTrip() {
        _rideState.value = RideState.TripEnded
    }

    // Reset simulation to initial state
    fun resetSimulation() {
        showRideModeBottomSheet()
    }

    // Toggle Ride mode option
    fun toggleRideMode(mode: RideModeState) {
        _rideModeState.value = mode
        when (mode) {
            RideModeState.OfferRideState -> showOfferRideBottomSheet()
            RideModeState.JoinRide -> showOfferRideBottomSheet()
            else -> TODO()
        }
    }

}
