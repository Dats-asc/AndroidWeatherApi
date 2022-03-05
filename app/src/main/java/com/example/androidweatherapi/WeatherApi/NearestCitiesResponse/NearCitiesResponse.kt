package com.example.androidweatherapi.WeatherApi.NearestCitiesResponse

data class NearCitiesResponse(
    val cod: String,
    val count: Int,
    val list: List<NearCity>,
    val message: String
)