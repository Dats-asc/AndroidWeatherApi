package com.example.androidweatherapi

data class CityDetail(
    val id: Int,
    val name: String,
    val temp: Int,
    val feelsLike: Int,
    val status: String,
    val humidity: String,
    val windSpeed: Int,
    val windDirection: String,
    val statusImgUrl: String
)