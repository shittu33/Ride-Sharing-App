package com.example.lincride.repositories

import android.util.Log
import com.example.lincride.utils.NetworkChecker
import com.example.lincride.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Base repository providing common network handling logic for all repositories.
 * Includes network connectivity checks and request wrapping with proper error handling.
 * 
 * Uses NetworkChecker interface for connectivity checks, allowing for different
 * implementations and easier testing without direct Android dependencies.
 */
open class BaseRepository @Inject constructor(
    private val networkChecker: NetworkChecker
) {

    /**
     * Wraps a network request with connectivity check and error handling.
     * Emits Loading state first, then either Success or Error based on network availability
     * and request execution.
     *
     * In production, this pattern can also be implemented in a network interceptor
     * for centralized handling at the OkHttp level.
     *
     * @param action The suspend function that performs the network request
     * @return Flow emitting NetworkResult states
     */
    protected fun <T> checkNetworkAndStartRequest(
        action: suspend () -> NetworkResult<T>
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading)

        Log.d("BaseRepository", "hasInternetConnection:${networkChecker.hasInternetConnection()}")
        if (!networkChecker.hasInternetConnection()) {
            emit(NetworkResult.Error.NetworkError(message = "No internet connection available"))
            return@flow
        }

        try {
            val result = action()
            emit(result)
        } catch (e: Exception) {
            emit(NetworkResult.Error.NetworkError(message = e.message ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}
