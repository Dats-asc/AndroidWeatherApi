package com.example.androidweatherapi.domain.usecase

import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(name: String): Weather {
        return weatherRepository.getWeatherByCityName(name)
    }
}