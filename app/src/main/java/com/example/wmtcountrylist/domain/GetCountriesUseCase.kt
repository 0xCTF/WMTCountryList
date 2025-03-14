package com.example.wmtcountrylist.domain

import com.example.wmtcountrylist.data.Country
import com.example.wmtcountrylist.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetCountriesUseCase {
    fun execute(): Flow<ResultWrapper<List<Country>>>
}