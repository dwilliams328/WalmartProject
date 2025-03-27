package com.example.walmartcodingassessment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcodingassessment.R
import com.example.walmartcodingassessment.data.repository.CountryRepository
import com.example.walmartcodingassessment.data.service.CountriesService
import com.example.walmartcodingassessment.data.service.ApiResult
import com.example.walmartcodingassessment.databinding.ActivityCountriesBinding
import com.example.walmartcodingassessment.ui.adapter.CountriesAdapter
import com.example.walmartcodingassessment.ui.viewmodel.CountriesViewModel
import com.example.walmartcodingassessment.ui.viewmodel.factory.CountriesViewModelFactory
import kotlinx.coroutines.launch

/**
 * UI for displaying countries list
 */
class CountriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountriesBinding
    private lateinit var rvCountries: RecyclerView
    private lateinit var countriesAdapter: CountriesAdapter
    private val viewModel: CountriesViewModel by viewModels {
        val apiService = CountriesService.getCountriesService()
        val countriesRepository = CountryRepository(apiService)
        CountriesViewModelFactory(countriesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        rvCountries = binding.rvCountries
        rvCountries.apply {
            countriesAdapter = CountriesAdapter()
            adapter = countriesAdapter
            addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@CountriesActivity)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countryListItemsState.collect { result ->
                    when (result) {
                        is ApiResult.Failure -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@CountriesActivity,
                                result.error.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is ApiResult.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ApiResult.Success -> {
                            binding.progressBar.visibility = View.GONE
                            countriesAdapter.updateData(result.data)
                        }

                        ApiResult.Uninitialized -> {}
                    }
                }
            }
        }
    }
}