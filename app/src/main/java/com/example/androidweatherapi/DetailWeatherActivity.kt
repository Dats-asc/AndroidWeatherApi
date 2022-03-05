package com.example.androidweatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidweatherapi.WeatherApi.WeatherRepository
import com.example.androidweatherapi.WeatherApi.WeatherResponse.WeatherResponse
import com.example.androidweatherapi.databinding.ActivityDetailWeatherBinding
import kotlinx.coroutines.launch

class DetailWeatherActivity : AppCompatActivity() {

    private val repository by lazy {
        WeatherRepository()
    }

    private lateinit var binding: ActivityDetailWeatherBinding

    private var CITY_ID = "CITY_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            initData(getCityDetailWeather(intent?.extras?.getInt(CITY_ID) ?: 0)!!)
        }

    }

    private fun initData(detailWeather: WeatherResponse) {
        Glide.with(this)
            .load("https://openweathermap.org/img/wn/${detailWeather.weather[0].icon}@4x.png")
            .into(binding.ivWeatherIcon)
        with(binding) {
            tvCity.text = detailWeather.name
            tvTemp.text = detailWeather.main.temp.toInt().toString() + "℃"
            tvFeelsLike.text = "Ощущается как: ${detailWeather.main.feels_like.toInt()}℃"
            tvStatus.text = detailWeather.weather[0].description
            tvHumidity.text = "Влажность: ${detailWeather.main.humidity.toString()}%"
            tvWindDirection.text = "Направление ветра: ${getWindDirection(detailWeather.wind.deg)}"
            tvWindSpeed.text = "Скорость ветраа: ${detailWeather.wind.speed}км/ч"
        }

    }

    private suspend fun getCityDetailWeather(cityId: Int): WeatherResponse? {
        var response: WeatherResponse? = null
        lifecycleScope.launch {
            response = repository.getWeatherByCityId(cityId)
        }.join()

        return response
    }

    private fun getWindDirection(deg: Int): String {
        var windDirection = ""
        when (deg) {
            in 0..15 -> windDirection = "северный"
            in 15..75 -> windDirection = "северо-восточный"
            in 75..105 -> windDirection = "восточный"
            in 105..165 -> windDirection = "юго-восточный"
            in 165..195 -> windDirection = "южный"
            in 195..255 -> windDirection = "юго-западный"
            in 255..285 -> windDirection = "западный"
            in 285..345 -> windDirection = "северо-западный"
            in 345..360 -> windDirection = "северный"
        }
        return windDirection
    }
}