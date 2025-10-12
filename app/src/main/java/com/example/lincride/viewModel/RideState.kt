package com.example.lincride.viewModel

sealed class RideState {
    data object Initial : RideState()
//    data object OfferRideBottomSheet : RideState()
    data class DrivingToPickup(val progress: Float) : RideState()
    data object PickupConfirmation : RideState()
    data object RiderAction : RideState()
    data class DrivingToDestination(val progress: Float) : RideState()
    data object TripEnded : RideState()
}
