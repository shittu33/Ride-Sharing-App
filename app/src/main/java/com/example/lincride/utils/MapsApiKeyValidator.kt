package com.example.lincride.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log

/**
 * Utility for checking Google Maps API key status.
 * Validates if the API key is properly configured in the app.
 */
object MapsApiKeyValidator {

    /**
     * Checks if Google Maps API key is properly configured.
     * 
     * @param context Application context
     * @return ApiKeyStatus indicating the current state
     */
    fun checkApiKeyStatus(context: Context): ApiKeyStatus {
        return try {
            val appInfo: ApplicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )

            val apiKey = appInfo.metaData?.getString("com.google.android.geo.API_KEY")

            when {
                apiKey.isNullOrBlank() -> ApiKeyStatus.Missing
                apiKey == "\${MAPS_API_KEY}" -> ApiKeyStatus.NotConfigured
                apiKey.length < 30 -> ApiKeyStatus.Invalid // Google API keys are typically 39 characters
                else -> ApiKeyStatus.Valid
            }
        } catch (e: Exception) {
            ApiKeyStatus.Error(e.message ?: "Unknown error")
        }
    }

    /**
     * Sealed class representing the API key status.
     */
    sealed class ApiKeyStatus {
        data object Valid : ApiKeyStatus()
        data object Missing : ApiKeyStatus()
        data object NotConfigured : ApiKeyStatus()
        data object Invalid : ApiKeyStatus()
        data class Error(val message: String) : ApiKeyStatus()

        fun isValid(): Boolean = this is Valid

        fun getErrorMessage(): String {
            return when (this) {
                is Valid -> ""
                is Missing -> "Google Maps API key is missing from AndroidManifest.xml"
                is NotConfigured -> "Google Maps API key is not configured. Please set MAPS_API_KEY in local.properties"
                is Invalid -> "Google Maps API key appears to be invalid (too short)"
                is Error -> "Error checking API key: $message"
            }
        }
    }
}
