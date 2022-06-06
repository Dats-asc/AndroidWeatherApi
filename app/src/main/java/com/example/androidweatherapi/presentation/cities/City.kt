package com.example.androidweatherapi.presentation.cities

data class City(
    val id: Int,
    val name: String,
    val temp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val tempColor: Int
)