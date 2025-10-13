package com.example.lincride.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

/**
 * Android implementation of NetworkChecker using ConnectivityManager.
 * This implementation checks for WiFi, Cellular, and Ethernet connections.
 */
class AndroidNetworkChecker @Inject constructor(
    private val context: Context
) : NetworkChecker {

    /**
     * Checks if the device has an active internet connection.
     * This uses ConnectivityManager to check for active network capabilities.
     *
     * @return true if connected via WiFi, Cellular, or Ethernet, false otherwise
     */
    override fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
