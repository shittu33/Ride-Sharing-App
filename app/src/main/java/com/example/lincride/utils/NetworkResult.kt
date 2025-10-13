package com.example.lincride.utils

/**
 * A sealed class representing the state of an asynchronous network or data operation.
 * It encapsulates success data, loading state, and various error types.
 */
sealed class NetworkResult<out R> {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    data object Loading: NetworkResult<Nothing>()

    sealed class Error : NetworkResult<Nothing>() {
        data class NetworkError(val code: Int? = null, val message: String = "Please check your internet connection") : Error() {
            override fun toString(): String {
                return message
            }
        }

        data class TimeoutError(val code: Int? = null, val message: String = "Network timeout!") : Error() {
            override fun toString(): String {
                return message
            }
        }

        data class NoDataError(val code: Int? = null, val message: String = "Something went wrong (No data)") : Error() {
            override fun toString(): String {
                return message
            }
        }

        /**
         * Error gotten from the response body, usually containing custom error messages
         * from the API.
         */
        data class ApiError(val message: String?, val error: Map<Any, Any>? = null, val code: Int? = null) : Error() {
            override fun toString(): String {
                return message ?: "An error occurred"
            }
        }
    }
}
