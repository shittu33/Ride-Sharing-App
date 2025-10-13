package com.example.lincride.api

import com.example.lincride.utils.NetworkResult

/**
 * This interface defines the API endpoints for Linc Ride.
 * It is intended to be used with Retrofit for production usage.
 */
interface RideService {

    /**
     * Retrieves rider profile information.
     *
     * @return NetworkResult containing rider info data
     */
    suspend fun getRiderInfo(): NetworkResult<String>

    /**
     * Retrieves the ride history for the current rider.
     *
     * @return NetworkResult containing ride history data
     */
    suspend fun getRideHistory(): NetworkResult<String>

}
