package kmp.shared.samplefeature.presentation.vm

import kmp.shared.analytics.domain.model.ToastAnalytics
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase.Params
import kmp.shared.base.domain.error.domain.CommonError
import kmp.shared.base.domain.model.Result
import kmp.shared.samplefeature.domain.model.Joke
import kmp.shared.samplefeature.domain.usecase.GetRandomJokeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

@Ignore // Cannot use molecule in JVM tests
class SampleFeatureViewModelTest {

    private lateinit var mockGetRandomJoke: GetRandomJokeUseCase
    private lateinit var mockTrackAnalytics: TrackAnalyticsEventUseCase
    private lateinit var viewModel: SampleFeatureViewModel

    @BeforeTest
    fun setUp() {
        mockGetRandomJoke = createMockGetRandomJokeUseCase()
        mockTrackAnalytics = createMockTrackAnalyticsUseCase()
        viewModel = SampleFeatureViewModel(mockGetRandomJoke, mockTrackAnalytics)
    }

    @Test
    fun `initial state has loading true`() = runBlocking {
        // When
        val initialState = viewModel.state.first()

        // Then
        assertTrue(initialState.loading)
        assertNull(initialState.joke)
        assertNull(initialState.error)
    }

    @Test
    fun `onViewAppeared loads joke successfully`() = runBlocking {
        // Given
        val expectedJoke = Joke(
            id = 1L,
            type = "programming",
            setup = "Why do programmers prefer dark mode?",
            punchline = "Because light attracts bugs!",
        )
        mockGetRandomJoke = object : GetRandomJokeUseCase {
            override suspend fun invoke(): Result<Joke> = Result.Success(expectedJoke)
        }
        viewModel = SampleFeatureViewModel(mockGetRandomJoke, mockTrackAnalytics)

        // When
        viewModel.onViewAppeared()

        // Then - wait for state to update (filter out initial loading state)
        val state = withTimeout(5.seconds) {
            viewModel.state.filter { !it.loading && it.joke != null }.first()
        }
        assertTrue(!state.loading)
        assertNotNull(state.joke)
        assertEquals(expectedJoke, state.joke)
        assertNull(state.error)
    }

    @Test
    fun `onViewAppeared handles error correctly`() = runBlocking {
        // Given
        val expectedError = CommonError.Unknown
        mockGetRandomJoke = object : GetRandomJokeUseCase {
            override suspend fun invoke(): Result<Joke> = Result.Error(expectedError)
        }
        viewModel = SampleFeatureViewModel(mockGetRandomJoke, mockTrackAnalytics)

        // When
        viewModel.onViewAppeared()

        // Then - wait for state to update (filter for non-loading state with error)
        val state = withTimeout(5.seconds) {
            viewModel.state.filter { !it.loading && it.error != null }.first()
        }
        assertTrue(!state.loading)
        assertNull(state.joke)
        assertNotNull(state.error)
        assertEquals(expectedError, state.error)
    }

    @Test
    fun `onViewAppeared sets loading to true then false`() = runBlocking {
        // When
        viewModel.onViewAppeared()

        // Then - wait for state to update (filter out initial loading state)
        val finalState = withTimeout(5.seconds) {
            viewModel.state.filter { !it.loading }.first()
        }
        assertTrue(!finalState.loading)
    }

    @Test
    fun `OnButtonTapped intent emits ShowMessage event`() = runBlocking {
        // Given
        var trackedEvent: ToastAnalytics.ToastPresentedEvent? = null
        mockTrackAnalytics = object : TrackAnalyticsEventUseCase {
            override suspend fun invoke(params: Params): Result<Unit> {
                trackedEvent = params.event as? ToastAnalytics.ToastPresentedEvent
                return Result.Success(Unit)
            }
        }
        viewModel = SampleFeatureViewModel(mockGetRandomJoke, mockTrackAnalytics)

        // Start collecting events before triggering the intent
        val eventChannel = Channel<SampleFeatureEvent>(Channel.UNLIMITED)
        val eventJob = launch {
            viewModel.events.collect { event ->
                eventChannel.trySend(event)
            }
        }

        // Small delay to ensure collection is active
        kotlinx.coroutines.delay(50)

        // When
        viewModel.onIntent(SampleFeatureIntent.OnButtonTapped)

        // Then - wait for the event to be collected
        val event = withTimeout(5.seconds) {
            eventChannel.receive()
        }
        eventJob.cancel()
        eventChannel.close()

        assertIs<SampleFeatureEvent.ShowMessage>(event)
        assertEquals("Button was tapped", event.message)
        assertNotNull(trackedEvent)
        assertEquals("shared_vm", trackedEvent?.parameters?.get("presented_from"))
    }

    @Test
    fun `OnButtonTapped intent tracks analytics event`() = runBlocking {
        // Given
        var analyticsCalled = false
        mockTrackAnalytics = object : TrackAnalyticsEventUseCase {
            override suspend fun invoke(params: Params): Result<Unit> {
                analyticsCalled = true
                assertIs<ToastAnalytics.ToastPresentedEvent>(params.event)
                return Result.Success(Unit)
            }
        }
        viewModel = SampleFeatureViewModel(mockGetRandomJoke, mockTrackAnalytics)

        // Start collecting events before triggering the intent
        val eventChannel = Channel<SampleFeatureEvent>(Channel.UNLIMITED)
        val eventJob = launch {
            viewModel.events.collect { event ->
                eventChannel.trySend(event)
            }
        }

        // Small delay to ensure collection is active
        kotlinx.coroutines.delay(50)

        // When
        viewModel.onIntent(SampleFeatureIntent.OnButtonTapped)

        // Then - wait for the event to ensure analytics was called
        withTimeout(5.seconds) {
            eventChannel.receive()
        }
        eventJob.cancel()
        eventChannel.close()

        assertTrue(analyticsCalled)
    }

    // Helper functions
    private fun createMockGetRandomJokeUseCase(): GetRandomJokeUseCase {
        return object : GetRandomJokeUseCase {
            override suspend fun invoke(): Result<Joke> = Result.Success(
                Joke(
                    id = 1L,
                    type = "test",
                    setup = "Test",
                    punchline = "Test",
                ),
            )
        }
    }

    private fun createMockTrackAnalyticsUseCase(): TrackAnalyticsEventUseCase {
        return object : TrackAnalyticsEventUseCase {
            override suspend fun invoke(params: Params): Result<Unit> = Result.Success(Unit)
        }
    }
}
