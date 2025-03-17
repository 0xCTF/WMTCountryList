package com.example.wmtcountrylist.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.wmtcountrylist.api.ApiService
import com.example.wmtcountrylist.api.CountryDto
import com.example.wmtcountrylist.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CountryRepository(private val apiService: ApiService) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun getCountries(): ResultWrapper<List<Country>> {
        return try {
            withContext(Dispatchers.IO) {
                val response: List<CountryDto> = apiService.getCountries()
                val mappedCountries = response.map { it.toDomainModel() } // ✅ Convert DTO to Domain Model
                ResultWrapper.Success(mappedCountries)
            }
        } catch (e: IOException) {
            ResultWrapper.Error("Network Error! Please check your connection.")
        } catch (e: HttpException) {
            ResultWrapper.Error("Server Error: ${e.message}")
        } catch (e: Exception) {
            ResultWrapper.Error("Unexpected Error: ${e.localizedMessage}")
        }
    }
}
