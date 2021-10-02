package com.example.weatherapp.di

import com.example.weatherapp.models.WeatherCondition
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemote {
    @GET("weather")
    suspend fun getWeatherByCityName(@Query("q") cityName: String): WeatherCondition

    @GET("weather")
    suspend fun getWeatherByLocation(@Query("lat") lat: Double, @Query("lon") long: Double): WeatherCondition

}