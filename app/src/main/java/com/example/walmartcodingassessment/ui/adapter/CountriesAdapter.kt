package com.example.walmartcodingassessment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.walmartcodingassessment.data.model.CountryDO
import com.example.walmartcodingassessment.databinding.CountryListItemRowBinding

/**
 * Countries RecyclerView adapter
 */
class CountriesAdapter(private var countriesList: List<CountryDO> = emptyList()) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    private lateinit var binding: CountryListItemRowBinding

    inner class CountriesViewHolder(itemRowBinding: CountryListItemRowBinding) : ViewHolder(itemRowBinding.root) {

        fun bind(country: CountryDO) {
            "${country.name}, ${country.region}".also { binding.countryAndRegion.text = it }
            binding.countryCode.text = country.code
            binding.capital.text = country.capital
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = CountryListItemRowBinding.inflate(layoutInflater, parent, false)

        return CountriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countriesList[position])
    }

    fun updateData(updatedList: List<CountryDO>) {
        countriesList = updatedList
        notifyDataSetChanged()
    }
}