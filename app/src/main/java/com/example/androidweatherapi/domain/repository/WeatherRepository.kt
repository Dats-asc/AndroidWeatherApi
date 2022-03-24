package com.example.androidweatherapi.domain.repository

import com.example.androidweatherapi.domain.entity.citylistitem.CityListItem
import com.example.androidweatherapi.domain.entity.detail.Weather

interface WeatherRepository {

    suspend fun getWeatherByCityName(name: String) : Weather

    suspend fun getWeatherByLocation(longitude: Double, latitude: Double) : Weather

    suspend fun getWeatherByCityId(id: Int) : Weather

    suspend fun getNearCities(longitude: Double, latitude: Double, count: Int) : List<CityListItem>
}