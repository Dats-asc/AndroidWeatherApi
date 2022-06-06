package com.example.androidweatherapi.data.api

import com.example.androidweatherapi.data.api.mapper.WeatherMapper
import com.example.androidweatherapi.domain.entity.citylistitem.CityListItem
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.repository.WeatherRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherMapper: WeatherMapper
) : WeatherRepository {

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

    override suspend fun getWeatherByCityName(name: String): Weather {
        return weatherMapper.mapWeatherResponse(api.getWeatherByCityName(name))
    }

    override suspend fun getWeatherByLocation(longitude: Double, latitude: Double) : Weather{
        return weatherMapper.mapWeatherResponse(api.getWeatherByLocation(longitude, latitude))
    }

    override suspend fun getWeatherByCityId(cityId: Int) : Weather{
        return weatherMapper.mapWeatherResponse(api.getWeatherByCityId(cityId))
    }

    override suspend fun getNearCities(longitude: Double, latitude: Double, count: Int) : List<CityListItem> {
        return weatherMapper.mapNearCitiesResponse(api.getNearCities(longitude, latitude, count))
    }
}