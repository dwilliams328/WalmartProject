package com.example.walmartcodingassessment.data.repository

import com.example.walmartcodingassessment.data.model.CountryDO
import com.example.walmartcodingassessment.data.service.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * Interface for country repository
 */
interface ICountryRepository {
    suspend fun getCountriesList(): Flow<ApiResult<List<CountryDO>>>
}