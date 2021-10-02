package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.networkModule
import com.example.weatherapp.di.repositoriesModule
import com.example.weatherapp.di.viewModelsModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // initialize koin modules
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            modules(
                listOf(networkModule, repositoriesModule, viewModelsModules)
            )
        }
    }
}