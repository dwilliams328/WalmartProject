package com.example.walmartcodingassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartcodingassessment.data.model.CountryDO
import com.example.walmartcodingassessment.data.repository.ICountryRepository
import com.example.walmartcodingassessment.data.service.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Countries ViewModel for providing UI state
 */
class CountriesViewModel(private val repo: ICountryRepository) : ViewModel() {

    private val _countryListItemsState = MutableStateFlow<ApiResult<List<CountryDO>>>(ApiResult.Uninitialized)
    val countryListItemsState: StateFlow<ApiResult<List<CountryDO>>> = _countryListItemsState.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCountriesList().collect {
                _countryListItemsState.value = it
            }
        }
    }
}