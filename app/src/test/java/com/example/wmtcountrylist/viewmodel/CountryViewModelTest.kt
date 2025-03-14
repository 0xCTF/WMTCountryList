package com.example.wmtcountrylist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.wmtcountrylist.domain.GetCountriesUseCase
import com.example.wmtcountrylist.presentation.viewmodel.CountryViewModel
import com.example.wmtcountrylist.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CountryViewModel
    private val getCountriesUseCase: GetCountriesUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getCountriesUseCase.execute() } returns flowOf(ResultWrapper.Success(listOf()))

        viewModel = CountryViewModel(getCountriesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCountries should emit Loading then Success`() = runTest {
        viewModel.fetchCountries()
        advanceUntilIdle()
        assertTrue(viewModel.countries.value is ResultWrapper.Success)
    }

    @Test
    fun `fetchCountries should emit Loading then Error`() = runTest {
        coEvery { getCountriesUseCase.execute() } returns flowOf(ResultWrapper.Error("Network Error"))

        viewModel.fetchCountries()
        advanceUntilIdle()
        assertTrue(viewModel.countries.value is ResultWrapper.Error)
    }
}
