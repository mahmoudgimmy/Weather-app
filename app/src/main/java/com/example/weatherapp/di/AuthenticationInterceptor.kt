package com.example.weatherapp.di

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {


        var newRequest = chain.request()
        val url: HttpUrl = newRequest.url.newBuilder()
            .addQueryParameter("appid", WEATHER_APP_KEY).build()
        newRequest = newRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}

private const val WEATHER_APP_KEY: String = "6bbee5339fad1b787df3650b1e4c6407"
