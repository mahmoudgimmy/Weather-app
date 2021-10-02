package com.example.weatherapp.ui.weather.viewmodels

import com.example.weatherapp.models.WeatherCondition

sealed class WeatherConditionViewState {
    object LOADING : WeatherConditionViewState()
    class SUCCESS(val payload: WeatherCondition) : WeatherConditionViewState()
    class FAILURE(val errorMsg: String) : WeatherConditionViewState()
}