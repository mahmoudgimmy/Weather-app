package com.example.weatherapp.di


import com.example.weatherapp.ui.weather.repositories.IWeatherRepository
import com.example.weatherapp.ui.weather.repositories.WeatherRepository
import com.example.weatherapp.ui.weather.viewmodels.WeatherViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"

// network module used for providing dependencies for calling apis, di used by koin
val networkModule = module {
    // provide singleton instance from AuthenticationInterceptor
    single { AuthenticationInterceptor() }

    // provide singleton instance from HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // provide singleton instance from HTTP Client
    single { provideOkHttpClient(get(), get()) }

    // provide singleton instance from Retrofit
    single { provideRetrofit(get()) }

    // provide singleton instance from Retrofit
    single { provideRemoteWeatherService(get()) }

}

// repositories module used for providing dependencies for repositories, di used by koin
@OptIn(KoinApiExtension::class)
val repositoriesModule = module {
    // provide singleton instance from WeatherRepository class
    single<IWeatherRepository> { WeatherRepository(get()) }
}

// viewModels module used for providing dependencies for viewModels, di used by koin
@OptIn(KoinApiExtension::class)
val viewModelsModules = module {
    // provide viewModel instance from WeatherViewModel class
    viewModel { WeatherViewModel(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(
    httpInterceptor: HttpLoggingInterceptor,
    authenticationInterceptor: AuthenticationInterceptor
): OkHttpClient {

    return OkHttpClient()
        .newBuilder()
        .addInterceptor(authenticationInterceptor)
        .addInterceptor(httpInterceptor)
        .build()
}

fun provideRemoteWeatherService(retrofit: Retrofit): WeatherRemote =
    retrofit.create(WeatherRemote::class.java)


