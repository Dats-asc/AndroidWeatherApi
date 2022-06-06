package com.example.androidweatherapi.domain.entity.citylistitem

data class CityListItem(
    val id: Int,
    val name: String,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double
)