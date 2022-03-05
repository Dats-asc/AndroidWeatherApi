package com.example.androidweatherapi.recyclerview

import android.graphics.Color

data class City(
    val id: Int,
    val name: String,
    val temp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val tempColor: Int
)