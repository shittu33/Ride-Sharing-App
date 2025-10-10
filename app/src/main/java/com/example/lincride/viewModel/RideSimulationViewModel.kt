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

    // Trigger Event 1: Show "Offer a Ride" Bottom Sheet
    fun showRideModeBottomSheet() {
        _rideState.value = RideState.Initial
    }

    // Trigger Event 2: Show "Offer a Ride" Bottom Sheet
     fun showOfferRideBottomSheet() {
        _rideState.value = RideState.OfferRideBottomSheet
    }

    // Start driving simulation to pickup (Event 3)
    fun startDrivingToPickup() {
        viewModelScope.launch {
            // Simulate progress updates from 0 to 1
            for (i in 1..10) {
                _rideState.value = RideState.DrivingToPickup(i / 10f)
                kotlinx.coroutines.delay(500)
            }
            _rideState.value = RideState.PickupConfirmation
        }
    }

    // Trigger Event 4: Show Rider Action Bottom Sheet
    fun onPickupConfirmed() {
        _rideState.value = RideState.RiderAction
    }

    // Start driving simulation to destination (Event 5)
    fun startDrivingToDestination() {
        viewModelScope.launch {
            for (i in 1..10) {
                _rideState.value = RideState.DrivingToDestination(i / 10f)
                kotlinx.coroutines.delay(500)
            }
            _rideState.value = RideState.TripEnded
        }
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
            RideModeState.JoinRide -> {
                TODO("Implement Join Ride functionality")
            }

            else -> TODO()
        }
    }

}
