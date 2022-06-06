package com.example.androidweatherapi.presentation.cities

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidweatherapi.domain.entity.citylistitem.CityListItem

class CityAdapter (
    private val cities: List<CityListItem>,
    private val action: (Int) -> Unit
) : RecyclerView.Adapter<CityHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, action)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int = cities.size
}