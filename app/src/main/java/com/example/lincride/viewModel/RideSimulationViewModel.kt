package com.example.lincride.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideSimulationViewModel @Inject constructor() : ViewModel() {

    private val _rideState = MutableStateFlow<RideState>(RideState.Initial)
    val rideState: StateFlow<RideState> = _rideState.asStateFlow()

    //keep track if simulation is cancelled
    private val _cancelSimulationState = MutableStateFlow<List<RideState>>(mutableListOf())

    private val _rideModeState = MutableStateFlow<RideModeState>(RideModeState.OfferRideState)
    val rideModeState: StateFlow<RideModeState> = _rideModeState.asStateFlow()

    // Progress state for car movement (0f to 1f)
    private val _carMovementProgress = MutableStateFlow(0f)
    val carMovementProgress: StateFlow<Float> = _carMovementProgress.asStateFlow()

    private var _riderStateJob: Job? = null

    // Update car movement progress
    fun updateCarMovementProgress(progress: Float) {
        _riderStateJob = viewModelScope.launch {
            _carMovementProgress.value = progress.coerceIn(0f, 1f)
        }
    }

    // Trigger Event 1: Show "Offer a Ride" Bottom Sheet
    private fun showRideModeBottomSheet() {
        _riderStateJob = viewModelScope.launch {
            _rideState.value = RideState.Initial
        }
    }

    // Start driving simulation to pickup (Event 3)
    private fun startDrivingToPickup() {
        _riderStateJob = viewModelScope.launch {
            _carMovementProgress.value = 0f // Reset progress
            _rideState.value = RideState.DrivingToPickup(0f)
        }
    }

    // Trigger Event 4: Show Rider Action Bottom Sheet
    fun showConfirmPickupBottomSheet() {
        _riderStateJob = viewModelScope.launch {
            _rideState.value = RideState.RiderAction
        }
    }

    // Start driving simulation to destination (Event 5)
    fun startDrivingToDestination() {
        _riderStateJob = viewModelScope.launch {
            _carMovementProgress.value = 0f // Reset progress
            _rideState.value = RideState.DrivingToDestination(0f)
        }
    }

    // End trip - arrival at destination
    fun endTrip() {
        _riderStateJob = viewModelScope.launch {
            _rideState.value = RideState.TripEnded
        }
    }

    // Reset simulation to initial state
    fun resetSimulation() {
        _riderStateJob?.cancel()
        _cancelSimulationState.value = emptyList()
        showRideModeBottomSheet()
    }

    // Toggle Ride mode option
    fun toggleRideMode(mode: RideModeState) {
        _rideModeState.value = mode
        when (mode) {
            RideModeState.OfferRideState -> startDrivingToPickup()
            RideModeState.JoinRide -> startDrivingToPickup()
            else -> TODO()
        }
    }


    fun startNextSimulation() {
        val nextState = when (_rideState.value) {
            is RideState.Initial -> {
                RideState.DrivingToPickup(0f)
            }

            is RideState.DrivingToPickup -> {
                RideState.RiderAction
            }

            is RideState.RiderAction -> {
                RideState.DrivingToDestination(0f)
            }

            is RideState.DrivingToDestination -> {
                RideState.TripEnded
            }

            else -> RideState.Initial
        }

        //cancel any ongoing job
        _riderStateJob?.cancel()

        // set the cancel state to current state
        addToCancelRideState(_rideState.value)

        // start the next state
        _riderStateJob = viewModelScope.launch {
            _rideState.value = nextState
        }
    }

    // Check if current ride state is added to cancelled states
    private inline fun <reified T:RideState> isRideStateCanceled() =
        _cancelSimulationState.value.filterIsInstance<T>().isNotEmpty()

    val isDrivingToPickupCancelled: Boolean
        get() = isRideStateCanceled<RideState.DrivingToPickup>()

    val isDrivingToDestinationCancelled: Boolean
        get() = isRideStateCanceled<RideState.DrivingToDestination>()



    private fun addToCancelRideState(rideState: RideState) {
        // add to the list of cancelled states
        _cancelSimulationState.value += listOf(rideState)
    }

    private fun removeFromCancelRideState(rideState: RideState) {
        // remove from the list of cancelled states
        _cancelSimulationState.value -= rideState
    }


}
