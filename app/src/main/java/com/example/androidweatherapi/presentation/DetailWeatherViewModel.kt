package com.example.androidweatherapi.presentation

import androidx.lifecycle.*
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailWeatherViewModel(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
) : ViewModel() {

    private var _detailWeather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val detailWeather: LiveData<Result<Weather>> = _detailWeather

    fun getWeather(cityId: Int) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByIdUseCase.invoke(cityId)
                _detailWeather.value = Result.success(weather)
            } catch (ex: Exception) {
                _detailWeather.value = Result.failure(ex)
            }
        }
    }
}