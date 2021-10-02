package com.example.weatherapp.ui.weather.repositories

import com.example.weatherapp.di.WeatherRemote
import com.example.weatherapp.models.WeatherCondition

class WeatherRepository(private val weatherRemote: WeatherRemote) : IWeatherRepository {

    override suspend fun getWeatherbyCityName(cityName: String): WeatherCondition =
        weatherRemote.getWeatherByCityName(cityName)

    override suspend fun getWeatherbyLocation(lat: Double, long: Double): WeatherCondition =
        weatherRemote.getWeatherByLocation(lat, long)


}