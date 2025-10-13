package com.example.lincride.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.Rule

/**
 * Unit tests for RideSimulationViewModel
 */
@ExperimentalCoroutinesApi
@DisplayName("RideSimulationViewModel Tests")
class RideSimulationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RideSimulationViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RideSimulationViewModel()
    }

    @AfterEach
    fun cleanup() {
        Dispatchers.resetMain()
    }

    // Complete Event Flow (Event 1 → Event 5)
    @Test
    fun `complete Event 1-5 flow executes in correct sequence`() = runTest {
        // Event 1: App launch
        assertEquals(RideState.Initial, viewModel.rideState.value)

        // Event 2: User clicks "Offer a Ride"
        viewModel.toggleRideMode(RideModeState.OfferRideState)
        testScheduler.advanceUntilIdle()
        assertTrue(viewModel.rideState.value is RideState.DrivingToPickup)

        // Event 3: Car arrives at pickup
        viewModel.showConfirmPickupBottomSheet()
        testScheduler.advanceUntilIdle()
        assertEquals(RideState.RiderAction, viewModel.rideState.value)

        // Event 4: User swipes to confirm pickup
        viewModel.startDrivingToDestination()
        testScheduler.advanceUntilIdle()
        assertTrue(viewModel.rideState.value is RideState.DrivingToDestination)

        // Event 5: Car arrives at destination
        viewModel.endTrip()
        testScheduler.advanceUntilIdle()
        assertEquals(RideState.TripEnded, viewModel.rideState.value)
    }

    // Reset Simulation 

    @Nested
    @DisplayName("Reset Simulation Tests")
    inner class ResetSimulationTest {
        @Test
        fun `resetSimulation returns to Event 1 state`() = runTest {
            // Complete a full ride
            viewModel.toggleRideMode(RideModeState.OfferRideState)
            testScheduler.advanceUntilIdle()
            viewModel.showConfirmPickupBottomSheet()
            testScheduler.advanceUntilIdle()
            viewModel.startDrivingToDestination()
            testScheduler.advanceUntilIdle()

            // Reset simulation
            viewModel.resetSimulation()
            testScheduler.advanceUntilIdle()

            // Should return to initial state
            assertEquals(RideState.Initial, viewModel.rideState.value)
        }


        @Test
        fun `resetSimulation clears all cancellation flags`() = runTest {
            // Skip through some states
            viewModel.toggleRideMode(RideModeState.OfferRideState)
            testScheduler.advanceUntilIdle()
            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()

            // Reset
            viewModel.resetSimulation()
            testScheduler.advanceUntilIdle()

            // All cancellations should be cleared
            assertFalse(viewModel.isDrivingToPickupCancelled)
            assertFalse(viewModel.isDrivingToDestinationCancelled)
        }
    }


    // startNextSimulation (Skip Ahead Feature)
    @Nested
    @DisplayName("Start Next Simulation Tests")
    inner class StartNextSimulationTest {
        @Test
        fun `startNextSimulation allows skipping from Initial to DrivingToPickup`() = runTest {
            assertEquals(RideState.Initial, viewModel.rideState.value)

            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()

            assertTrue(viewModel.rideState.value is RideState.DrivingToPickup)
        }

        @Test
        fun `startNextSimulation allows skipping from DrivingToPickup to RiderAction`() = runTest {
            viewModel.toggleRideMode(RideModeState.OfferRideState)
            testScheduler.advanceUntilIdle()

            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()

            assertEquals(RideState.RiderAction, viewModel.rideState.value)
        }

        @Test
        fun `startNextSimulation allows skipping from RiderAction to DrivingToDestination`() =
            runTest {
                viewModel.showConfirmPickupBottomSheet()
                testScheduler.advanceUntilIdle()

                viewModel.startNextSimulation()
                testScheduler.advanceUntilIdle()

                assertTrue(viewModel.rideState.value is RideState.DrivingToDestination)
            }

        @Test
        fun `startNextSimulation allows skipping from DrivingToDestination to TripEnded`() =
            runTest {
                viewModel.showConfirmPickupBottomSheet()
                testScheduler.advanceUntilIdle()
                viewModel.startNextSimulation()
                testScheduler.advanceUntilIdle()

                viewModel.startNextSimulation()
                testScheduler.advanceUntilIdle()

                assertEquals(RideState.TripEnded, viewModel.rideState.value)
            }

        @Test
        fun `startNextSimulation marks skipped DrivingToPickup as cancelled`() = runTest {
            viewModel.toggleRideMode(RideModeState.OfferRideState)
            testScheduler.advanceUntilIdle()
            assertFalse(viewModel.isDrivingToPickupCancelled)

            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()

            assertTrue(viewModel.isDrivingToPickupCancelled)
        }

        @Test
        fun `startNextSimulation marks skipped DrivingToDestination as cancelled`() = runTest {
            viewModel.showConfirmPickupBottomSheet()
            testScheduler.advanceUntilIdle()
            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()
            assertFalse(viewModel.isDrivingToDestinationCancelled)

            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()

            assertTrue(viewModel.isDrivingToDestinationCancelled)
        }

        @Test
        fun `cancellation flags persist across multiple state transitions`() = runTest {
            // Skip Event 3
            viewModel.toggleRideMode(RideModeState.OfferRideState)
            testScheduler.advanceUntilIdle()
            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()
            assertTrue(viewModel.isDrivingToPickupCancelled)

            // Skip Event 5
            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()
            viewModel.startNextSimulation()
            testScheduler.advanceUntilIdle()

            // Both should remain cancelled
            assertTrue(viewModel.isDrivingToPickupCancelled)
            assertTrue(viewModel.isDrivingToDestinationCancelled)
        }

        // -- End startNextSimulation Tests --
    }
}
