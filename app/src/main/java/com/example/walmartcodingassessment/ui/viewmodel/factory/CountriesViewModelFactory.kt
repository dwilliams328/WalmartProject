package com.example.walmartcodingassessment.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walmartcodingassessment.data.repository.ICountryRepository
import com.example.walmartcodingassessment.ui.viewmodel.CountriesViewModel

/**
 * Factory for creating ViewModel instance
 */
class CountriesViewModelFactory(private val repo: ICountryRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountriesViewModel::class.java)) {
            return CountriesViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}