package com.example.lincride.viewModel

sealed class RideModeState {
    data object JoinRide : RideModeState()
    data object OfferRideState : RideModeState()
}
