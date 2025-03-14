package com.example.wmtcountrylist.api

import com.example.wmtcountrylist.data.Country

data class CountryDto(
    val name: String,
    val region: String,
    val code: String,
    val capital: String
) {
    fun toDomainModel(): Country {
        return Country(
            name = name,
            region = region,
            code = code,
            capital = capital
        )
    }
}
