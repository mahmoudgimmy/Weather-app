package com.example.weatherapp.ui.weather.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.ui.weather.repositories.IWeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: IWeatherRepository) : ViewModel() {
    private val _weatherCondition = MutableLiveData<WeatherConditionViewState>()
    val weatherCondition: LiveData<WeatherConditionViewState> = _weatherCondition

    fun getWeatherByCityName(cityName: String) = viewModelScope.launch {
        _weatherCondition.value = WeatherConditionViewState.LOADING

        try {
            val result = repository.getWeatherbyCityName(cityName)
            _weatherCondition.value = WeatherConditionViewState.SUCCESS(payload = result)
        } catch (e: Exception) {
            _weatherCondition.value =
                WeatherConditionViewState.FAILURE(errorMsg = "City Not Found!")
        }

    }

    fun getWeatherByLocation(location: Location) = viewModelScope.launch {
        _weatherCondition.value = WeatherConditionViewState.LOADING

        try {
            val result = repository.getWeatherbyLocation(location.latitude, location.longitude)
            _weatherCondition.value = WeatherConditionViewState.SUCCESS(payload = result)
        } catch (e: Exception) {
            _weatherCondition.value =
                WeatherConditionViewState.FAILURE(errorMsg = "City Not Found!")
        }


    }

}