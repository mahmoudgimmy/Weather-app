package com.example.weatherapp.ui.weather.repositories

import com.example.weatherapp.models.WeatherCondition

interface IWeatherRepository {
    suspend fun getWeatherbyCityName(cityName:String): WeatherCondition
    suspend fun getWeatherbyLocation(lat:Double, long:Double): WeatherCondition
}