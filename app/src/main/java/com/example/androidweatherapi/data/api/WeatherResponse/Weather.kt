package com.example.androidweatherapi.data.api.WeatherResponse

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)