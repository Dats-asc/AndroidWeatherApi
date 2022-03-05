package com.example.androidweatherapi.WeatherApi

import com.example.androidweatherapi.WeatherApi.NearestCitiesResponse.NearCitiesResponse
import com.example.androidweatherapi.WeatherApi.WeatherResponse.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("https://api.openweathermap.org/data/2.5/weather?&appid=0ec207db705f0608c1d203ad06737e72&lang=ru")
    suspend fun getWeather(@Query("q") city: String): WeatherResponse

    @GET("https://api.openweathermap.org/data/2.5/weather?&appid=0ec207db705f0608c1d203ad06737e72&lang=ru")
    suspend fun getWeatherByLocation(@Query("lon") longitude: Double,
                                     @Query("lat") latitude: Double) : WeatherResponse

    @GET("https://api.openweathermap.org/data/2.5/weather?&appid=0ec207db705f0608c1d203ad06737e72&lang=ru")
    suspend fun getWeatherByCityId(@Query("id") cityId: Int) : WeatherResponse

    @GET("https://api.openweathermap.org/data/2.5/find?&appid=0ec207db705f0608c1d203ad06737e72&lang=ru")
    suspend fun getNearCities(@Query("lon") longitude: Double,
                              @Query("lat") latitude: Double,
                              @Query("cnt") count: Int) : NearCitiesResponse
}