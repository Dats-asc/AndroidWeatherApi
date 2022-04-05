package com.example.androidweatherapi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailWeatherViewModel @Inject constructor(
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