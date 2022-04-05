package com.example.androidweatherapi.di.module

import com.example.androidweatherapi.data.api.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

@Module
class NetModule {

    @Provides
    @Named("units")
    fun unitsInterceptor(): Interceptor = Interceptor { chain ->
        chain.run {
            val updatedRequestUrl = request().url.newBuilder()
                .addQueryParameter("units", "metric")
                .build()

            proceed(
                request().newBuilder().url(updatedRequestUrl).build()
            )
        }
    }

    @Provides
    fun provieGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun okhttp(
        @Named("units") unitsInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(unitsInterceptor)
            .build()

    @Provides
    fun api(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): WeatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(WeatherApi::class.java)
}