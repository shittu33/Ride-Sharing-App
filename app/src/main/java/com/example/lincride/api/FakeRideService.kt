package com.example.lincride.api

import com.example.lincride.utils.NetworkResult
import kotlinx.coroutines.delay

/**
 * This is a fake implementation of RideService used for testing and development.
 * The production application would use the actual RideService interface with Retrofit.
 */
class FakeRideService : RideService {

    /**
     * Simulates retrieving rider profile information with network delay.
     */
    override suspend fun getRiderInfo(): NetworkResult<String> {
        delay(500)
        return NetworkResult.Success("Simulated rider info: John Doe, Rating: 4.8")
    }

    /**
     * Simulates retrieving ride history with network delay.
     */
    override suspend fun getRideHistory(): NetworkResult<String> {
        delay(500)
        return NetworkResult.Success("Simulated ride history: 45 completed rides")
    }

}
