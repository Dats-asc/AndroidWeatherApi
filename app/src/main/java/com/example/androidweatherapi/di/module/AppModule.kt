package com.example.androidweatherapi.di.module

import com.example.androidweatherapi.data.api.WeatherRepositoryImpl
import com.example.androidweatherapi.data.api.mapper.WeatherMapper
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.repository.WeatherRepository
import com.example.androidweatherapi.domain.usecase.GetNearCitiesUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherByLocationUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideWeatherMapper() : WeatherMapper = WeatherMapper()
}