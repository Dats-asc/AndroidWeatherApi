package com.example.androidweatherapi.data.api.mapper

import com.example.androidweatherapi.data.api.WeatherResponse.*
import com.example.androidweatherapi.domain.entity.detail.Location
import com.example.androidweatherapi.domain.entity.detail.Weather

class WeatherMapper {

    fun map(response: WeatherResponse): Weather = Weather(
        base = response.base,
        clouds = mapClouds(response.clouds),
        cod = response.cod,
        coord = mapCoord(response.coord),
        id = response.id,
        dt = response.dt,
        main = mapMain(response.main),
        name = response.name,
        sys = mapSys(response.sys),
        timezone = response.timezone,
        visibility = response.visibility,
        weather = mapWeather(response.weather),
        wind = mapWind(response.wind),


        )

    private fun mapMain(responseMain: Main) =
        com.example.androidweatherapi.domain.entity.detail.Main(
            feels_like = responseMain.feels_like,
            grnd_level = responseMain.grnd_level,
            humidity = responseMain.humidity,
            pressure = responseMain.pressure,
            sea_level = responseMain.sea_level,
            temp = responseMain.temp,
            temp_max = responseMain.temp_max,
            temp_min = responseMain.temp_min
        )

    private fun mapClouds(responseClouds: Clouds) =
        com.example.androidweatherapi.domain.entity.detail.Clouds(
            all = responseClouds.all
        )

    private fun mapCoord(responseCoord: Coord) =
        com.example.androidweatherapi.domain.entity.detail.Coord(
            lat = responseCoord.lat,
            lon = responseCoord.lon
        )

    private fun mapSys(responseSys: Sys) = com.example.androidweatherapi.domain.entity.detail.Sys(
        country = responseSys.country,
        id = responseSys.id,
        sunrise = responseSys.sunrise,
        sunset = responseSys.sunset,
        type = responseSys.type
    )

    private fun mapWind(responseWind: Wind) =
        com.example.androidweatherapi.domain.entity.detail.Wind(
            deg = responseWind.deg,
            speed = responseWind.speed,
            gust = responseWind.gust
        )

    private fun mapWeather(responseWeather: com.example.androidweatherapi.data.api.WeatherResponse.Weather) =
        Location(
            description = responseWeather.description,
            icon = responseWeather.icon,
            id = responseWeather.id,
            main = responseWeather.main
        )

    private fun mapWeather(responseWeather: List<com.example.androidweatherapi.data.api.WeatherResponse.Weather>): List<Location>{
        var mappedList = mutableListOf<Location>()
        responseWeather.forEach{ city ->
            mappedList.add(mapWeather(city))
        }

        return mappedList
    }
}