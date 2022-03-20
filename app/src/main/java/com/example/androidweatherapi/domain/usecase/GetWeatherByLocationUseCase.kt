package com.example.androidweatherapi.domain.usecase

import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.repository.WeatherRepository

class GetWeatherByLocationUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(longitude: Double, latitude: Double): Weather {
        return weatherRepository.getWeatherByLocation(longitude, latitude)
    }
}