package com.example.androidweatherapi.WeatherApi.WeatherResponse

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)