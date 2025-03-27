package com.example.walmartcodingassessment.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provide retrofit instance
 */
object RetrofitClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/"

    fun getRetrofitInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        return retrofit
    }
}