package com.example.wmtcountrylist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wmtcountrylist.data.Country
import com.example.wmtcountrylist.domain.GetCountriesUseCase
import com.example.wmtcountrylist.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _countries = MutableStateFlow<ResultWrapper<List<Country>>>(ResultWrapper.Loading)
    val countries: StateFlow<ResultWrapper<List<Country>>> = _countries

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { result ->
                _countries.value = result
            }
        }
    }
}
