package com.example.androidweatherapi.domain.usecase

import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.repository.WeatherRepository

class GetWeatherByIdUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(id: Int): Weather {
        return weatherRepository.getWeatherByCityId(id)
    }
}