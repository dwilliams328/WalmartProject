package com.example.walmartcodingassessment.data.repository

import com.example.walmartcodingassessment.data.model.CountryDO
import com.example.walmartcodingassessment.data.service.CountriesService
import com.example.walmartcodingassessment.data.service.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * CountryRepository fetches data from api and handle error scenarios
 */
class CountryRepository(
    private val apiService: CountriesService
) : ICountryRepository {

    override suspend fun getCountriesList(): Flow<ApiResult<List<CountryDO>>> =
        flow {
            emit(ApiResult.Loading)
            try {
                val response = apiService.getCountriesList()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ApiResult.Success(data = it))
                    } ?: emit(ApiResult.Failure(error = Exception("Empty response body")))
                } else {
                    emit(ApiResult.Failure(error = Exception("Network failure response code: ${response.code()}")))
                }
            } catch (e: Exception) {
                emit(ApiResult.Failure(error = Exception("Failed try/catch: $e")))
            }
        }.flowOn(Dispatchers.IO)

}
