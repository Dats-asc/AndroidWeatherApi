package com.example.androidweatherapi.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.androidweatherapi.App
import com.example.androidweatherapi.databinding.ActivityDetailWeatherBinding
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import com.example.androidweatherapi.utils.ViewModelFactory
import javax.inject.Inject

class DetailWeatherActivity : AppCompatActivity() {

    private lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    private lateinit var binding: ActivityDetailWeatherBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: DetailWeatherViewModel by viewModels{
        factory
    }

    private var CITY_ID = "CITY_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()

        viewModel.getWeather(intent?.extras?.getInt(CITY_ID) ?: 0)
    }
    private fun initObservers() {
        viewModel.detailWeather.observe(this) {
            it.fold(onSuccess = {
                initData(it)
            }, onFailure = {
                Log.e("", "wrong id")
            })
        }
    }

    private fun initData(detailWeather: Weather) {
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