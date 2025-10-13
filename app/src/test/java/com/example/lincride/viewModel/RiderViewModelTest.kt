package com.example.lincride.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lincride.repositories.FakeRideRepository
import com.example.lincride.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class RiderViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RiderViewModel
    private lateinit var mockRepository: FakeRideRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock repository
        mockRepository = mock(FakeRideRepository::class.java)
        
        // Create ViewModel with mocked repository
        viewModel = RiderViewModel(mockRepository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    // Test fetchRiderInfo
    @Test
    fun `fetchRiderInfo emits Loading then Success`() = runTest {
        // Mock successful response
        val mockData = "John Doe, Rating: 4.8"
        val successResult = NetworkResult.Success(mockData)
        
        `when`(mockRepository.getRiderInfo()).thenReturn(
            flowOf(NetworkResult.Loading, successResult)
        )

        // Fetch rider info
        viewModel.fetchRiderInfo()
        testScheduler.advanceUntilIdle()

        // Verify state is Success
        val state = viewModel.riderInfoState.value
        assertTrue(state is NetworkResult.Success)
        assertEquals(mockData, (state as NetworkResult.Success).data)
    }

    @Test
    fun `fetchRiderInfo emits Loading then NetworkError when offline`() = runTest {
        // Mock network error response
        val errorResult = NetworkResult.Error.NetworkError(
            message = "No internet connection available"
        )
        
        `when`(mockRepository.getRiderInfo()).thenReturn(
            flowOf(NetworkResult.Loading, errorResult)
        )

        // Fetch rider info
        viewModel.fetchRiderInfo()
        testScheduler.advanceUntilIdle()

        // Verify state is NetworkError
        val state = viewModel.riderInfoState.value
        assertTrue(state is NetworkResult.Error.NetworkError)
        assertEquals("No internet connection available", (state as NetworkResult.Error.NetworkError).message)
    }


    // Test fetchRideHistory
    @Test
    fun `fetchRideHistory emits Loading then Success`() = runTest {
        // Mock successful response
        val mockData = "45 completed rides"
        val successResult = NetworkResult.Success(mockData)
        
        `when`(mockRepository.getRideHistory()).thenReturn(
            flowOf(NetworkResult.Loading, successResult)
        )

        // Observe LiveData
        val observer = androidx.lifecycle.Observer<NetworkResult<String>> { }
        viewModel.rideHistoryState.observeForever(observer)

        // Fetch ride history
        viewModel.fetchRideHistory()
        testScheduler.advanceUntilIdle()

        // Verify state is Success
        val state = viewModel.rideHistoryState.value
        assertTrue(state is NetworkResult.Success)
        assertEquals(mockData, (state as NetworkResult.Success).data)

        // Clean up
        viewModel.rideHistoryState.removeObserver(observer)
    }

    @Test
    fun `fetchRideHistory emits Loading then NetworkError when offline`() = runTest {
        // Mock network error response
        val errorResult = NetworkResult.Error.NetworkError(
            message = "No internet connection available"
        )
        
        `when`(mockRepository.getRideHistory()).thenReturn(
            flowOf(NetworkResult.Loading, errorResult)
        )

        // Observe LiveData
        val observer = androidx.lifecycle.Observer<NetworkResult<String>> { }
        viewModel.rideHistoryState.observeForever(observer)

        // Fetch ride history
        viewModel.fetchRideHistory()
        testScheduler.advanceUntilIdle()

        // Verify state is NetworkError
        val state = viewModel.rideHistoryState.value
        assertTrue(state is NetworkResult.Error.NetworkError)

        // Clean up
        viewModel.rideHistoryState.removeObserver(observer)
    }

    // Test multiple sequential calls
    @Test
    fun `multiple fetchRiderInfo calls update state correctly`() = runTest {
        // First call - Success
        val firstData = "First fetch: John Doe"
        `when`(mockRepository.getRiderInfo()).thenReturn(
            flowOf(NetworkResult.Loading, NetworkResult.Success(firstData))
        )

        viewModel.fetchRiderInfo()
        testScheduler.advanceUntilIdle()

        val firstState = viewModel.riderInfoState.value
        assertEquals(firstData, (firstState as NetworkResult.Success).data)

        // Second call - Different data
        val secondData = "Second fetch: Jane Smith"
        `when`(mockRepository.getRiderInfo()).thenReturn(
            flowOf(NetworkResult.Loading, NetworkResult.Success(secondData))
        )

        viewModel.fetchRiderInfo()
        testScheduler.advanceUntilIdle()

        val secondState = viewModel.riderInfoState.value
        assertEquals(secondData, (secondState as NetworkResult.Success).data)
    }


}
