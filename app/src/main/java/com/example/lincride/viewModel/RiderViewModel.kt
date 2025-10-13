package com.example.lincride.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lincride.repositories.FakeRideRepository
import com.example.lincride.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for rider-related data and operations.
 * Manages UI state and coordinates data flow from the repository.
 */
@HiltViewModel
class RiderViewModel @Inject constructor(
    private val repository: FakeRideRepository
) : ViewModel() {

    // Rider Info State
    private val _riderInfoState = MutableStateFlow<NetworkResult<String>>(NetworkResult.Loading)
    val riderInfoState: StateFlow<NetworkResult<String>> = _riderInfoState.asStateFlow()

    // In a typical production app, Ride History will be paginated
    // which will require Paging 3 library but for simplicity,
    // we use LiveData here
    private val _rideHistoryState = MutableLiveData<NetworkResult<String>>()
    val rideHistoryState: LiveData<NetworkResult<String>> = _rideHistoryState

    // Ride Request State
    private val _rideRequestState = MutableStateFlow<NetworkResult<String>>(NetworkResult.Loading)
    val rideRequestState: StateFlow<NetworkResult<String>> = _rideRequestState.asStateFlow()

    /**
     * Fetches rider profile information.
     * Updates riderInfoState with Loading, Success, or Error.
     */
    fun fetchRiderInfo() {
        viewModelScope.launch {
            repository.getRiderInfo().collect { result ->
                _riderInfoState.value = result
            }
        }
    }

    /**
     * Fetches ride history for the current rider.
     * Updates rideHistoryState with Loading, Success, or Error.
     */
    fun fetchRideHistory() {
        viewModelScope.launch {
            repository.getRideHistory().collect { result ->
                _rideHistoryState.value = result
            }
        }
    }

    /**
     * Resets the ride request state to Loading.
     * Useful for clearing previous request results.
     */
    fun resetRideRequestState() {
        _rideRequestState.value = NetworkResult.Loading
    }
}
