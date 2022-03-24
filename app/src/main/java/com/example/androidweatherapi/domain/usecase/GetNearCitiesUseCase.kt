package com.example.androidweatherapi.domain.usecase

import com.example.androidweatherapi.domain.entity.citylistitem.CityListItem
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.repository.WeatherRepository

class GetNearCitiesUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(longitude: Double, latitude: Double, count: Int): List<CityListItem> {
        return weatherRepository.getNearCities(longitude, latitude, count)
    }
}