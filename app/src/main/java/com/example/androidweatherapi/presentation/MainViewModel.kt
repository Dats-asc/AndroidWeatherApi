package com.example.androidweatherapi.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidweatherapi.domain.entity.citylistitem.CityListItem
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.usecase.GetNearCitiesUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getNearCitiesUseCase: GetNearCitiesUseCase
) : ViewModel() {

    private var _queryWeather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val queryWeather: LiveData<Result<Weather>> = _queryWeather

    private var _nearCityWeather: MutableLiveData<Result<List<CityListItem>>> = MutableLiveData()
    val nearCityWeather: LiveData<Result<List<CityListItem>>> = _nearCityWeather

    fun onQuerySubmit(query: String?) {
        viewModelScope.launch {
            try {
                val response = getWeatherUseCase.invoke(query ?: "")
                _queryWeather.value = Result.success(response)
            } catch (ex: Exception) {
                Log.e("ds", ex.message.toString())
                _queryWeather.value = Result.failure(ex)
            }
        }
    }

    fun getNearCities(longitude: Double, latitude: Double, count: Int) {
        viewModelScope.launch {
            try {
                val nearCitiesWeather = getNearCitiesUseCase.invoke(longitude, latitude, count)
                _nearCityWeather.value = Result.success(nearCitiesWeather)
            } catch (ex: Exception) {
                _nearCityWeather.value = Result.failure(ex)
            }
        }
    }
}