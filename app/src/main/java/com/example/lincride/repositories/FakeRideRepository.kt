package com.example.lincride.repositories

import com.example.lincride.api.RideService
import com.example.lincride.utils.NetworkChecker
import com.example.lincride.utils.NetworkResult
import com.example.lincride.utils.getResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Test repository for ride-related data operations.
 * Uses FakeRideService for development and testing purposes.
 */
@Singleton
class FakeRideRepository @Inject constructor(
    private val apiService: RideService,
    networkChecker: NetworkChecker
) : BaseRepository(networkChecker) {

    /**
     * Fetches rider profile information.
     *
     * @return Flow emitting NetworkResult with rider info
     */
    fun getRiderInfo(): Flow<NetworkResult<String>> = checkNetworkAndStartRequest {
        apiService.getRiderInfo().getResult()
    }

    /**
     * Fetches ride history for the current rider.
     *
     * @return Flow emitting NetworkResult with ride history
     */
    fun getRideHistory(): Flow<NetworkResult<String>> = checkNetworkAndStartRequest {
        apiService.getRideHistory().getResult()
    }

}
