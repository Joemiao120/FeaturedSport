package com.example.featuredsport.viewmodel

import app.cash.turbine.test
import com.example.featuredsport.data.model.Sport
import com.example.featuredsport.data.model.SportUiState
import com.example.featuredsport.domain.GetRandomSport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SportViewModelTest {
    private lateinit var viewModel: SportViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = SportViewModel(mock())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshSport sets loading true`() = runTest {
        viewModel.refreshSport()

        val uiState = viewModel.uiState.first()

        assertTrue(uiState.loading)
        assert(uiState.currentSport == null)
    }

    @Test
    fun `refreshSport sets loading false and sport`() = runTest {
        val testSport = Sport(
            "MockName", "mock description"
        )

        val getRandomSport = mock<GetRandomSport>()
        whenever(getRandomSport()).thenReturn(testSport)
        val testViewModel = SportViewModel(getRandomSport)

        val eventList: MutableList<SportUiState> = mutableListOf()

        testViewModel.uiState.test {
            eventList.add(awaitItem())
            eventList.add(awaitItem())
            eventList.add(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        assert(
            eventList == mutableListOf(
                SportUiState(null, false),
                SportUiState(null, true),
                SportUiState(currentSport = testSport, false)
            )
        )
    }
}