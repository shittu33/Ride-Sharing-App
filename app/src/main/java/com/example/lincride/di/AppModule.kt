package com.example.lincride.di

import android.content.Context
import com.example.lincride.api.FakeRideService
import com.example.lincride.api.RideService
import com.example.lincride.repositories.FakeRideRepository
import com.example.lincride.utils.AndroidNetworkChecker
import com.example.lincride.utils.NetworkChecker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the FusedLocationProviderClient for location services.
     * This client is used to access the device's location.
     */
    @Provides
    @Singleton
    fun provideFusedLocationClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Provides the NetworkChecker implementation.
     * Uses AndroidNetworkChecker which wraps ConnectivityManager.
     * This abstraction allows for easier testing and different implementations.
     */
    @Provides
    @Singleton
    fun provideNetworkChecker(
        @ApplicationContext context: Context
    ): NetworkChecker {
        return AndroidNetworkChecker(context)
    }

    /**
     * Provides the RideService implementation.
     * Currently using FakeRideService for testing and development.
     * In production, this would be replaced with a Retrofit-based implementation.
     */
    @Provides
    @Singleton
    fun provideRideService(): RideService {
        return FakeRideService()
    }

    /**
     * Provides the FakeRideRepository for data operations.
     * NetworkChecker is injected automatically by Hilt.
     */
    @Provides
    @Singleton
    fun provideRideRepository(
        apiService: RideService,
        networkChecker: NetworkChecker
    ): FakeRideRepository {
        return FakeRideRepository(apiService, networkChecker)
    }
}
