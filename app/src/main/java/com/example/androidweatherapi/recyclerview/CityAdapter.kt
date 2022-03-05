package com.example.androidweatherapi.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidweatherapi.WeatherApi.NearestCitiesResponse.NearCity

class CityAdapter (
    private val cities: List<NearCity>,
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