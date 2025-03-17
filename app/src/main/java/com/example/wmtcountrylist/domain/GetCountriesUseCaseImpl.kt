package com.example.wmtcountrylist.domain

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.wmtcountrylist.data.Country
import com.example.wmtcountrylist.data.CountryRepository
import com.example.wmtcountrylist.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetCountriesUseCaseImpl(
    private val repository: CountryRepository
) : GetCountriesUseCase {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun execute(): Flow<ResultWrapper<List<Country>>> = flow {
        emit(ResultWrapper.Loading)
        val result = repository.getCountries()
        emit(result)
    }.catch { e ->
        emit(ResultWrapper.Error("Unexpected Error: ${e.localizedMessage}"))
    }
}
