package com.example.androidweatherapi.di.module

import com.example.androidweatherapi.data.api.WeatherRepositoryImpl
import com.example.androidweatherapi.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ) : WeatherRepository
}