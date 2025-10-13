package com.example.lincride.utils

/**
 * Extension functions and utilities for NetworkResult handling.
 */

/**
 * Gets the result from a NetworkResult, transforming it if necessary.
 *
 * This may sound redundant, but it's useful
 * in a production app
 */
fun <T> NetworkResult<T>.getResult(): NetworkResult<T> {
    return when (this) {
        is NetworkResult.Success -> {
            // In case of Success in prod, you often need
            // to transform json response to your data model
            // using Gson or Moshi
            NetworkResult.Success(data)
        }

        is NetworkResult.Loading -> NetworkResult.Loading
        is NetworkResult.Error -> getUserMessage()
    }
}

/**
 * Gets a user-friendly error message from any NetworkResult.Error.
 *
 * In production, you might want to map different error types
 */
fun NetworkResult.Error.getUserMessage(): NetworkResult.Error {
    return when (this) {
        is NetworkResult.Error.NetworkError -> NetworkResult.Error.NetworkError(code, message)
        is NetworkResult.Error.TimeoutError -> NetworkResult.Error.NetworkError(code, message)
        is NetworkResult.Error.NoDataError -> NetworkResult.Error.NetworkError(code, message)
        is NetworkResult.Error.ApiError -> {
            // In case of ApiError in prod, you can return message
            // based on error codes, or checking specific keys
            // in the api error.
            NetworkResult.Error.NetworkError(code, message ?: "An unexpected error occurred")

        }
    }
}

