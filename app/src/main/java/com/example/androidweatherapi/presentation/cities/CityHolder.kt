package com.example.androidweatherapi.presentation.cities

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidweatherapi.databinding.ItemCityBinding
import com.example.androidweatherapi.domain.entity.citylistitem.CityListItem

class CityHolder (
    private val binding: ItemCityBinding,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: CityListItem){
        with(binding){
            tvCity.text = item.name
            tvTemp.text = item.temp.toInt().toString() + "℃"
            tvMinTemp.text = item.minTemp.toInt().toString() + "°"
            tvMaxTemp.text = item.maxTemp.toInt().toString() + "°"
            when(item.temp.toInt()){
                in -40..-10 -> tvTemp.setTextColor(Color.BLUE)
                in -9..0 -> tvTemp.setTextColor(Color.GREEN)
                in 1..10 -> tvTemp.setTextColor(Color.WHITE)
                in 10..20 -> tvTemp.setTextColor(Color.YELLOW)
                in 10..40 -> tvTemp.setTextColor(Color.RED)
                else -> tvTemp.setTextColor(Color.GRAY)
            }
        }
        itemView.setOnClickListener{
            action(item.id)
        }
    }

    companion object{
        fun create(
            parent: ViewGroup,
            action: (Int) -> Unit
        ) = CityHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}