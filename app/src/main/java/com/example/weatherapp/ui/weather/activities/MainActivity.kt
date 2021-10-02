package com.example.weatherapp.ui.weather.activities

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.weatherapp.CurrentLocation
import com.example.weatherapp.GPSTracker
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.weather.viewmodels.WeatherConditionViewState
import com.example.weatherapp.ui.weather.viewmodels.WeatherViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        obtainCurrentLocation()
        initializeUI()
        initializeObservers()
    }

    private fun initializeUI() {
        val autocompleteFragment =
            (supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment?)!!
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                place.name?.let { viewModel.getWeatherByCityName(it) }
            }

            override fun onError(p0: Status) {
            }


        })
    }

    private fun initializeObservers() {
        viewModel.weatherCondition.observe(this, { render(it) })
    }

    private fun obtainCurrentLocation() {

        GPSTracker(this, object : CurrentLocation {

            override fun currentLocation(location: Location?) {

                location?.let {
                    viewModel.getWeatherByLocation(location)
                }
            }
        })

    }

    private fun render(viewState: WeatherConditionViewState) {
        when (viewState) {
            is WeatherConditionViewState.LOADING ->
                binding.pbLoading.isVisible = true

            is WeatherConditionViewState.FAILURE ->
                binding.pbLoading.isVisible = false

            is WeatherConditionViewState.SUCCESS -> {

                binding.apply {
                    pbLoading.isVisible = false

                    val weatherCondition = viewState.payload
                    tvCityName.text = weatherCondition.name
                    (weatherCondition.main.temp.toString() + " C").also { tvWeatherTemp.text = it }
                    (weatherCondition.weather[0].main + ", " + weatherCondition.weather[0].description).also {
                        tvWeatherStatus.text = it
                    }

                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val apiKey = getString(R.string.google_places_api_key)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        Places.createClient(this)
    }

}