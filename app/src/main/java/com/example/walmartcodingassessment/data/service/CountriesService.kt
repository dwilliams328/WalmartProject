package com.example.walmartcodingassessment.data.service

import com.example.walmartcodingassessment.data.model.CountryDO
import retrofit2.Response
import retrofit2.http.GET

/**
 * Countries api service
 */
interface CountriesService {
    @GET(END_POINT)
    suspend fun getCountriesList(): Response<List<CountryDO>>

    companion object {
        private const val END_POINT = "peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"
        private lateinit var INSTANCE: CountriesService

        /**
         * Creates implementation for ApiServiceInterface
         */
        fun getCountriesService(): CountriesService {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = RetrofitClient.getRetrofitInstance().create(CountriesService::class.java)
            }

            return INSTANCE
        }
    }
}