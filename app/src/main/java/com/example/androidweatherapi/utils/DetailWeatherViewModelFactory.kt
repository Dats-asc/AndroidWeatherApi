package com.example.androidweatherapi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherUseCase
import com.example.androidweatherapi.presentation.DetailWeatherViewModel
import com.example.androidweatherapi.presentation.MainViewModel

class DetailWeatherViewModelFactory(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        when {
            modelClass.isAssignableFrom(DetailWeatherViewModel::class.java) -> {
                DetailWeatherViewModel(getWeatherByIdUseCase) as? T
                    ?: throw IllegalArgumentException("Unknown viewmodel class")
            }

            else -> {
                throw IllegalArgumentException("Unknown viewmodel class")
            }
        }
}