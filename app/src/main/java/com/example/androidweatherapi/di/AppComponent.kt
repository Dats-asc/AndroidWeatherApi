package com.example.androidweatherapi.di

import com.example.androidweatherapi.di.module.AppModule
import com.example.androidweatherapi.di.module.NetModule
import com.example.androidweatherapi.di.module.RepoModule
import com.example.androidweatherapi.di.module.ViewModelModule
import com.example.androidweatherapi.presentation.DetailWeatherActivity
import com.example.androidweatherapi.presentation.MainActivity
import dagger.Component


@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(detailWeatherActivity: DetailWeatherActivity)
}