package com.example.wmtcountrylist.domain

import app.cash.turbine.test
import com.example.wmtcountrylist.data.Country
import com.example.wmtcountrylist.data.CountryRepository
import com.example.wmtcountrylist.utils.ResultWrapper
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCountriesUseCaseImplTest {

    private val repository: CountryRepository = mock()
    private val useCase = GetCountriesUseCaseImpl(repository)

    @Test
    fun `execute should emit Loading then Success`() = runTest {
        val countries = listOf(Country("Algeria", "Africa", "DZ", "Algiers"))
        whenever(repository.getCountries()).thenReturn(ResultWrapper.Success(countries))

        useCase.execute().test {
            assertEquals(ResultWrapper.Loading, awaitItem())
            assertEquals(ResultWrapper.Success(countries), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `execute should emit Loading then Error`() = runTest {
        val errorMessage = "Server Error!"
        whenever(repository.getCountries()).thenReturn(ResultWrapper.Error(errorMessage))

        useCase.execute().test {
            assertEquals(ResultWrapper.Loading, awaitItem())
            assertEquals(ResultWrapper.Error(errorMessage), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
