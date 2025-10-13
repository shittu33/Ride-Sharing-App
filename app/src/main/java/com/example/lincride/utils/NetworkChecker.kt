package com.example.lincride.utils

/**
 * Interface for checking network connectivity.
 * This abstraction allows for different implementations and easier testing.
 */
interface NetworkChecker {
    /**
     * Checks if the device has an active internet connection.
     *
     */
    fun hasInternetConnection(): Boolean
}
