package com.example.androidweatherapi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidweatherapi.domain.usecase.GetNearCitiesUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherUseCase
import com.example.androidweatherapi.presentation.DetailWeatherViewModel
import com.example.androidweatherapi.presentation.MainViewModel

class MainViewModelFactory(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getNearCitiesUseCase: GetNearCitiesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(getWeatherUseCase, getNearCitiesUseCase) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }

            else -> {
                throw IllegalArgumentException("Unknown viewmodel class")
            }
        }
}