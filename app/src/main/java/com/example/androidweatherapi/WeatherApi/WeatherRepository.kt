package com.example.androidweatherapi.WeatherApi

import com.example.androidweatherapi.WeatherApi.NearestCitiesResponse.NearCitiesResponse
import com.example.androidweatherapi.WeatherApi.WeatherResponse.WeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(unitsInterceptor)
            .build()
    }

    private val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    private val unitsInterceptor = Interceptor { chain ->
        chain.run {
            val updatedRequestUrl = request().url.newBuilder()
                .addQueryParameter("units", "metric")
                .build()

            proceed(
                request().newBuilder().url(updatedRequestUrl).build()
            )
        }
    }


    suspend fun getWeather(city: String): WeatherResponse {
        return api.getWeather(city)
    }

    suspend fun getWeatherByLocation(longitude: Double, latitude: Double) : WeatherResponse{
        return api.getWeatherByLocation(longitude, latitude)
    }

    suspend fun getWeatherByCityId(cityId: Int) : WeatherResponse{
        return api.getWeatherByCityId(cityId)
    }

    suspend fun getNearCities(longitude: Double, latitude: Double, count: Int) : NearCitiesResponse{
        return api.getNearCities(longitude, latitude, count)
    }
}