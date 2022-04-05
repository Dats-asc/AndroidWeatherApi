package com.example.androidweatherapi.data.api.NearestCitiesResponse

data class NearCitiesResponse(
    val cod: String,
    val count: Int,
    val list: List<NearCity>,
    val message: String
)